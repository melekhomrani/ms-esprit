#!/bin/bash

set -e

export KUBECONFIG=/mnt/c/Users/lek/.kube/config

echo "Deploying to Kubernetes..."

kubectl apply -f k8s/