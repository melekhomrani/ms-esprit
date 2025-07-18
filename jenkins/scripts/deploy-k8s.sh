#!/bin/bash

set -e

set -x

export MINIKUBE_HOME=/home/melek
export KUBECONFIG=/mnt/c/Users/lek/.kube/config

kubectl apply -f k8s/