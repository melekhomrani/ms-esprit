#!/bin/bash

set -e

export MINIKUBE_HOME=/home/melek
export KUBECONFIG=/mnt/c/Users/lek/.kube/config

echo "Pushing Docker images to Minikube..."

minikube image load ms-esprit/authentication:latest
minikube image load ms-esprit/config-server:latest
minikube image load ms-esprit/eureka-server:latest
minikube image load ms-esprit/gateway:latest
minikube image load ms-esprit/ms-manufacturer:latest
minikube image load ms-esprit/ms-product:latest
minikube image load ms-esprit/ms-notification:latest

echo "Docker images pushed to Minikube successfully."