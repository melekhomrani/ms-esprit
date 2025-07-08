#!/bin/bash
set -e
echo "Building and running authentication NodeJS service..."
npm cache clean --force
npm install -g npm
docker-compose up --build -d authentication-app
docker-compose logs -f authentication-app