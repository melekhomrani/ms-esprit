#!/bin/bash

set -e

eval $(minikube docker-env)

services=(authentication config-server eureka-server gateway ms-manufacturer ms-product ms-notification)

for service in "${services[@]}"; do
  echo "Building Docker image for $service"
  docker build -t $service:latest ./$service
done
