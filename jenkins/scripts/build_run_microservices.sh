#!/bin/bash
set -e
echo "Building and running ms-product, ms-manufacturer, ms-notification..."
docker-compose build ms-product ms-manufacturer ms-notification
docker-compose up -d ms-product ms-manufacturer ms-notification
