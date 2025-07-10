#!/bin/bash

# Kubernetes Deployment Script for MS-ESPRIT Microservices
# This script deploys all microservices to a Kubernetes cluster

set -e

echo "🚀 Starting deployment of MS-ESPRIT microservices to Kubernetes..."

# Apply ConfigMap and Secrets first
echo "📋 Applying ConfigMap and Secrets..."
kubectl apply -f configmap.yaml
kubectl apply -f secret.yaml

# Wait a moment for resources to be created
sleep 2

# Deploy infrastructure services first (config-server, eureka-server)
echo "🏗️ Deploying infrastructure services..."
kubectl apply -f config-server-deployment.yaml
kubectl apply -f eureka-server-deployment.yaml

# Wait for infrastructure services to be ready
echo "⏳ Waiting for infrastructure services to be ready..."
kubectl wait --for=condition=available --timeout=300s deployment/config-server
kubectl wait --for=condition=available --timeout=300s deployment/eureka-server

# Deploy application services
echo "🔧 Deploying application services..."
kubectl apply -f gateway-deployment.yaml
kubectl apply -f ms-product-deployment.yaml
kubectl apply -f ms-manufacturer-deployment.yaml
kubectl apply -f ms-notification-deployment.yaml
kubectl apply -f authentication-deployment.yaml

# Wait for application services to be ready
echo "⏳ Waiting for application services to be ready..."
kubectl wait --for=condition=available --timeout=300s deployment/gateway
kubectl wait --for=condition=available --timeout=300s deployment/ms-product
kubectl wait --for=condition=available --timeout=300s deployment/ms-manufacturer
kubectl wait --for=condition=available --timeout=300s deployment/ms-notification
kubectl wait --for=condition=available --timeout=300s deployment/authentication

# Deploy Ingress
echo "🌐 Deploying Ingress..."
kubectl apply -f ingress.yaml

# Display deployment status
echo "📊 Deployment Status:"
kubectl get deployments
echo ""
kubectl get services
echo ""
kubectl get ingress

echo "✅ Deployment completed successfully!"
echo ""
echo "📝 Next steps:"
echo "1. Add the following entries to your /etc/hosts file (or C:\Windows\System32\drivers\etc\hosts on Windows):"
echo "   $(minikube ip) ms-gateway.local"
echo "   $(minikube ip) auth.local"
echo ""
echo "2. Access your services:"
echo "   - Gateway: http://ms-gateway.local"
echo "   - Authentication: http://auth.local"
echo "   - Eureka Dashboard: http://$(minikube ip):8761"
echo ""
echo "3. Monitor your deployments:"
echo "   kubectl get pods -w"
