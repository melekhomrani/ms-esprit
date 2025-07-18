#!/bin/bash

set -e

export KUBECONFIG=/mnt/c/Users/lek/.kube/config

echo "Deploying to Kubernetes..."

minikube image load localhost:5000/authentication-app:latest
minikube image load localhost:5000/config-server:latest
minikube image load localhost:5000/eureka-server:latest
minikube image load localhost:5000/gateway:latest
minikube image load localhost:5000/ms-manufacturer:latest
minikube image load localhost:5000/ms-product:latest
minikube image load localhost:5000/ms-notification:latest

kubectl apply -f k8s/