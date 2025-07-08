#!/bin/bash
set -e
echo "Building and running authentication NodeJS service..."
docker-compose build authentication-app
docker-compose up -d authentication-app
