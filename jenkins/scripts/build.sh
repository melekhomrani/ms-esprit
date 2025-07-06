#!/bin/bash
echo "🔨 Building all microservices..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Build failed. Exiting..."
    exit 1
else
    echo "✅ Build completed successfully."
fi