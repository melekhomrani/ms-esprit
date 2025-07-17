#!/bin/bash

set -e 

echo "Deploying stack with Docker Compose..."

docker-compose down
docker-compose pull
docker-compose up -d --build