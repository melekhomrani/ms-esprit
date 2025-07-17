#!/bin/bash

set -e

eval $(minikube docker-env)

# services=(authentication config-server eureka-server gateway ms-manufacturer ms-product ms-notification)

docker build -t ms-esprit/authentication:latest -f ./authentication/Dockerfile ./authentication
docker build -t ms-esprit/config-server:latest -f ./config-server/Dockerfile ./config-server
docker build -t ms-esprit/eureka-server:latest -f ./eureka-server/Dockerfile ./eureka-server
docker build -t ms-esprit/gateway:latest -f ./gateway/Dockerfile ./gateway
docker build -t ms-esprit/ms-manufacturer:latest -f ./ms-manufacturer/Dockerfile ./ms-manufacturer
docker build -t ms-esprit/ms-product:latest -f ./ms-product/Dockerfile ./ms-product
docker build -t ms-esprit/ms-notification:latest -f ./ms-notification/Dockerfile ./ms-notification