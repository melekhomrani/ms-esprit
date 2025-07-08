#!/bin/bash
set -e
echo "Building and running authentication NodeJS service..."
docker-compose up --build -d authentication-app