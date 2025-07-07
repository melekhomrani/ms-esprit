#!/bin/bash
set -e
echo "Building and running eureka-server..."
docker-compose build eureka-server
docker-compose up -d eureka-server
