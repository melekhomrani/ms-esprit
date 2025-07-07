#!/bin/bash
echo "🐳 Deploying stack with Docker Compose..."
docker-compose down
docker-compose pull
docker-compose up -d --build
if [ $? -ne 0 ]; then
    echo "❌ Deployment failed. Exiting..."
    exit 1
else
    echo "✅ Deployment completed successfully."
fi