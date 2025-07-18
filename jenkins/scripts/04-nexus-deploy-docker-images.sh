#! /bin/bash

set -e
set -x

echo "Logging in to Nexus Docker registry..."

docker login localhost:5000 -u admin -p admin

echo "Deploying Docker images to Nexus..."

docker push localhost:5000/authentication:latest
docker push localhost:5000/config-server:latest
docker push localhost:5000/eureka-server:latest 
docker push localhost:5000/gateway:latest
docker push localhost:5000/ms-manufacturer:latest
docker push localhost:5000/ms-product:latest
docker push localhost:5000/ms-notification:latest
