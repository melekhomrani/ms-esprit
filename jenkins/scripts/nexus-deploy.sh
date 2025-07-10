#!/bin/bash

echo "🚀 Deploying artifacts to Nexus..."

mvn deploy -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Deployment to Nexus failed. Exiting..."
    exit 1
else
    echo "✅ Deployment to Nexus completed successfully."
fi