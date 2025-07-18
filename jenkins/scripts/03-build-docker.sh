#!/bin/bash

set -e
set -x

# services=(authentication config-server eureka-server gateway ms-manufacturer ms-product ms-notification)

docker login localhost:5000 -u admin -p admin

docker build -t localhost:5000/authentication:latest -f ./authentication/Dockerfile ./authentication
docker build -t localhost:5000/config-server:latest -f ./config-server/Dockerfile ./config-server
docker build -t localhost:5000/eureka-server:latest -f ./eureka-server/Dockerfile ./eureka-server
docker build -t localhost:5000/gateway:latest -f ./gateway/Dockerfile ./gateway
docker build -t localhost:5000/ms-manufacturer:latest -f ./ms-manufacturer/Dockerfile ./ms-manufacturer
docker build -t localhost:5000/ms-product:latest -f ./ms-product/Dockerfile ./ms-product
docker build -t localhost:5000/ms-notification:latest -f ./ms-notification/Dockerfile ./ms-notification