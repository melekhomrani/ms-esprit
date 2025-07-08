#!/bin/bash
set -e
echo "Building and running gateway..."
docker-compose build gateway
docker-compose up -d gateway
