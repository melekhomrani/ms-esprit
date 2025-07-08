#!/bin/bash
echo "🚀 Deploying artifacts to Nexus..."
mvn deploy -DaltDeploymentRepository=nexus::default::http://localhost:8081/repository/maven-releases/
if [ $? -ne 0 ]; then
    echo "❌ Deployment to Nexus failed. Exiting..."
    exit 1
else
    echo "✅ Deployment to Nexus completed successfully."
fi