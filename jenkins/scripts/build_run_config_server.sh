#!/bin/bash
set -e
echo "Building and running config-server..."
docker-compose build config-server
docker-compose up -d config-server
