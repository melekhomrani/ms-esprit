#!/bin/bash

# Docker Image Tag and Push Script for MS-ESPRIT Microservices
# This script builds, tags, and pushes all microservice images to local Nexus registry

set -e

# Configuration
NEXUS_REGISTRY="localhost:5000"
IMAGE_TAG="latest"

echo "🐳 Starting Docker image build, tag, and push process..."
echo "📍 Target registry: $NEXUS_REGISTRY"
echo "🏷️  Image tag: $IMAGE_TAG"
echo ""

# Function to build, tag, and push an image
build_tag_push() {
    local service_name=$1
    local dockerfile_path=$2
    
    echo "🔨 Building $service_name..."
    
    # Build the image
    docker build -t ms-esprit/$service_name:$IMAGE_TAG $dockerfile_path
    
    # Tag for Nexus registry
    docker tag ms-esprit/$service_name:$IMAGE_TAG $NEXUS_REGISTRY/$service_name:$IMAGE_TAG
    
    # Push to Nexus registry
    echo "📤 Pushing $service_name to $NEXUS_REGISTRY..."
    docker push $NEXUS_REGISTRY/$service_name:$IMAGE_TAG
    
    echo "✅ $service_name completed successfully!"
    echo ""
}

# Check if Nexus registry is accessible
echo "🔍 Checking Nexus registry accessibility..."
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

# Build and push all services
echo "🚀 Building and pushing all microservices..."
echo ""

# Infrastructure services
build_tag_push "config-server" "../config-server"
build_tag_push "eureka-server" "../eureka-server"

# Application services
build_tag_push "gateway" "../gateway"
build_tag_push "ms-product" "../ms-product"
build_tag_push "ms-manufacturer" "../ms-manufacturer"
build_tag_push "ms-notification" "../ms-notification"
build_tag_push "authentication" "../authentication"

# Display summary
echo "📋 Image Summary:"
echo "=================="
docker images | grep -E "(ms-esprit|localhost:5000)" | head -20

echo ""
echo "🎯 All images have been successfully built, tagged, and pushed!"
echo ""
echo "📝 Next steps:"
echo "1. Verify images in Nexus registry:"
echo "   Open: http://localhost:8081"
echo "   Go to: Browse → Repositories → docker-hosted"
echo ""
echo "2. Deploy to Kubernetes:"
echo "   ./deploy.sh"
echo ""
echo "3. Clean up local images (optional):"
echo "   docker image prune -f"
