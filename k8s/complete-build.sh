#!/bin/bash

# Complete Build, Tag, and Push Script for MS-ESPRIT Microservices
# This script builds Maven projects, Docker images, tags, and pushes to Nexus registry

set -e

# Configuration
NEXUS_REGISTRY="localhost:5000"
IMAGE_TAG="latest"
MAVEN_PROFILE="default"

echo "🚀 MS-ESPRIT Complete Build and Push Pipeline"
echo "=============================================="
echo "📍 Target registry: $NEXUS_REGISTRY"
echo "🏷️  Image tag: $IMAGE_TAG"
echo "☕ Maven profile: $MAVEN_PROFILE"
echo ""

# Function to build Maven project
build_maven_project() {
    local service_name=$1
    local project_path=$2
    
    echo "☕ Building Maven project: $service_name"
    cd "$project_path"
    
    # Clean and build the project
    if [ -f "./mvnw" ]; then
        echo "   Using Maven wrapper..."
        ./mvnw clean package -DskipTests -P$MAVEN_PROFILE
    else
        echo "   Using system Maven..."
        mvn clean package -DskipTests -P$MAVEN_PROFILE
    fi
    
    echo "✅ Maven build completed for $service_name"
    cd - > /dev/null
    echo ""
}

# Function to build, tag, and push Docker image
build_docker_image() {
    local service_name=$1
    local dockerfile_path=$2
    
    echo "🐳 Building Docker image: $service_name"
    
    # Build the image
    docker build -t ms-esprit/$service_name:$IMAGE_TAG $dockerfile_path
    
    # Tag for Nexus registry
    docker tag ms-esprit/$service_name:$IMAGE_TAG $NEXUS_REGISTRY/$service_name:$IMAGE_TAG
    
    # Push to Nexus registry
    echo "📤 Pushing $service_name to $NEXUS_REGISTRY..."
    docker push $NEXUS_REGISTRY/$service_name:$IMAGE_TAG
    
    echo "✅ Docker operations completed for $service_name"
    echo ""
}

# Function to build npm project (for authentication service)
build_npm_project() {
    local service_name=$1
    local project_path=$2
    
    echo "📦 Building npm project: $service_name"
    cd "$project_path"
    
    # Install dependencies
    if [ -f "package.json" ]; then
        echo "   Installing npm dependencies..."
        npm install
        
        # Run build if build script exists
        if npm run --silent 2>/dev/null | grep -q "build"; then
            echo "   Running npm build..."
            npm run build
        fi
    fi
    
    echo "✅ npm build completed for $service_name"
    cd - > /dev/null
    echo ""
}

# Check prerequisites
echo "🔍 Checking prerequisites..."

# Check Docker
if ! command -v docker &> /dev/null; then
    echo "❌ Docker is not installed or not in PATH"
    exit 1
fi

# Check if Docker is running
if ! docker info &> /dev/null; then
    echo "❌ Docker is not running. Please start Docker Desktop."
    exit 1
fi

# Check Maven (optional)
if command -v mvn &> /dev/null; then
    echo "✅ Maven found: $(mvn --version | head -1)"
else
    echo "ℹ️  Maven not found in PATH, will use Maven wrapper where available"
fi

# Check Node.js (optional)
if command -v node &> /dev/null; then
    echo "✅ Node.js found: $(node --version)"
else
    echo "ℹ️  Node.js not found in PATH"
fi

# Check Nexus registry
if ! curl -f http://localhost:8081/service/rest/v1/status > /dev/null 2>&1; then
    echo "❌ Warning: Nexus registry at localhost:8081 is not accessible"
    echo "   Please ensure Nexus is running before proceeding"
    echo "   You can start it with: docker run -d -p 8081:8081 -p 5000:5000 sonatype/nexus3"
    echo ""
    read -p "Do you want to continue anyway? (y/N): " -n 1 -r
    echo ""
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        echo "🛑 Aborting..."
        exit 1
    fi
fi

echo "✅ Prerequisites check completed"
echo ""

# Change to project root directory
cd "$(dirname "$0")/.."

# Build all Maven projects
echo "🔨 Building Maven projects..."
echo "=============================="

build_maven_project "config-server" "./config-server"
build_maven_project "eureka-server" "./eureka-server"
build_maven_project "gateway" "./gateway"
build_maven_project "ms-product" "./ms-product"
build_maven_project "ms-manufacturer" "./ms-manufacturer"
build_maven_project "ms-notification" "./ms-notification"

# Build npm project
echo "📦 Building npm projects..."
echo "==========================="
build_npm_project "authentication" "./authentication"

# Build and push all Docker images
echo "🐳 Building and pushing Docker images..."
echo "========================================"

build_docker_image "config-server" "./config-server"
build_docker_image "eureka-server" "./eureka-server"
build_docker_image "gateway" "./gateway"
build_docker_image "ms-product" "./ms-product"
build_docker_image "ms-manufacturer" "./ms-manufacturer"
build_docker_image "ms-notification" "./ms-notification"
build_docker_image "authentication" "./authentication"

# Display summary
echo "📋 Final Summary"
echo "================"
echo "Built and pushed images:"
docker images | grep -E "(ms-esprit|localhost:5000)" | head -20

echo ""
echo "🎯 Complete build and push pipeline completed successfully!"
echo ""
echo "📝 Next steps:"
echo "1. Verify images in Nexus registry:"
echo "   Open: http://localhost:8081"
echo "   Go to: Browse → Repositories → docker-hosted"
echo ""
echo "2. Deploy to Kubernetes:"
echo "   cd k8s && ./deploy.sh"
echo ""
echo "3. Monitor the deployment:"
echo "   kubectl get pods -w"
echo ""
echo "4. Clean up local images (optional):"
echo "   docker image prune -f"
