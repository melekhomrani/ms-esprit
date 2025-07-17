#!/bin/bash

set -e

echo "Deploying to Kubernetes..."

kubectl apply -f k8s/