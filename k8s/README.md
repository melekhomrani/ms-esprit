# Kubernetes Deployment for MS-ESPRIT Microservices

This directory contains Kubernetes manifests for deploying the MS-ESPRIT microservices project to a Kubernetes cluster using Minikube with Argo CD for GitOps.

## 📋 Prerequisites

1. **Minikube** installed and running
2. **kubectl** configured to connect to your Minikube cluster
3. **Docker images** built and pushed to your local Nexus registry (localhost:5000)
4. **Nginx Ingress Controller** enabled in Minikube:
   ```bash
   minikube addons enable ingress
   ```

## 🏗️ Architecture Overview

The deployment consists of the following services:

### Infrastructure Services
- **config-server** (port 8888): Centralized configuration management
- **eureka-server** (port 8761): Service discovery and registration

### Application Services
- **gateway** (port 8989): API Gateway with routing and security
- **ms-product** (port 8084): Product management service (MongoDB)
- **ms-manufacturer** (port 8083): Manufacturer management service (MySQL)
- **ms-notification** (port 8040): Notification service with email and Kafka
- **authentication** (port 5050): Node.js authentication service

## 📁 Files Description

| File | Description |
|------|-------------|
| `configmap.yaml` | Configuration variables for all services |
| `secret.yaml` | Sensitive data (passwords, tokens) - base64 encoded |
| `config-server-deployment.yaml` | Config Server deployment and service |
| `eureka-server-deployment.yaml` | Eureka Server deployment and service |
| `gateway-deployment.yaml` | API Gateway deployment and service |
| `ms-product-deployment.yaml` | Product service deployment and service |
| `ms-manufacturer-deployment.yaml` | Manufacturer service deployment and service |
| `ms-notification-deployment.yaml` | Notification service deployment and service |
| `authentication-deployment.yaml` | Authentication service deployment and service |
| `ingress.yaml` | Ingress configuration for external access |
| `deploy.sh` | Automated deployment script |
| `build-and-push.sh` | Docker build, tag, and push script (Linux/Mac) |
| `complete-build.sh` | Complete Maven + Docker build and push pipeline |

## 🚀 Deployment Instructions

### Step 1: Build and Push Images

Before deploying, you need to build and push your Docker images to the Nexus registry:

#### Option A: Complete Build (Recommended)
```bash
# Navigate to the k8s directory
cd k8s

# Make the script executable
chmod +x complete-build.sh

# Build Maven projects, Docker images, and push to registry
./complete-build.sh
```

#### Option B: Docker Only Build
```bash
# If you already have JAR files built
chmod +x build-and-push.sh
./build-and-push.sh
```

#### Option C: Windows Users
```powershell
# PowerShell version
.\build-and-push.ps1

# Or batch file version
.\build-and-push.bat
```

### Step 2: Deploy to Kubernetes

#### Option 1: Automated Deployment
```bash
# Navigate to the k8s directory
cd k8s

# Make the script executable
chmod +x deploy.sh

# Run the deployment script
./deploy.sh
```

### Option 2: Manual Deployment
```bash
# 1. Apply ConfigMap and Secrets
kubectl apply -f configmap.yaml
kubectl apply -f secret.yaml

# 2. Deploy infrastructure services first
kubectl apply -f config-server-deployment.yaml
kubectl apply -f eureka-server-deployment.yaml

# 3. Wait for infrastructure services to be ready
kubectl wait --for=condition=available --timeout=300s deployment/config-server
kubectl wait --for=condition=available --timeout=300s deployment/eureka-server

# 4. Deploy application services
kubectl apply -f gateway-deployment.yaml
kubectl apply -f ms-product-deployment.yaml
kubectl apply -f ms-manufacturer-deployment.yaml
kubectl apply -f ms-notification-deployment.yaml
kubectl apply -f authentication-deployment.yaml

# 5. Deploy Ingress
kubectl apply -f ingress.yaml
```

## 🔧 Configuration

### Environment Variables
All environment variables are managed through:
- **ConfigMap**: Non-sensitive configuration data
- **Secret**: Sensitive data like passwords and tokens

### Health Checks
- **Spring Boot services**: `/actuator/health` endpoint
- **Authentication service**: `/health` endpoint
- **Probe settings**: initialDelaySeconds=10, periodSeconds=5

### Resource Limits
- **Spring Boot services**: 512Mi-1Gi memory, 250m-500m CPU
- **Authentication service**: 256Mi-512Mi memory, 100m-250m CPU

## 🌐 Access Configuration

### Add to /etc/hosts (Linux/Mac) or C:\Windows\System32\drivers\etc\hosts (Windows):
```
<MINIKUBE_IP> ms-gateway.local
<MINIKUBE_IP> auth.local
```

Get Minikube IP:
```bash
minikube ip
```

### Service Access URLs
- **Gateway**: http://ms-gateway.local
- **Authentication**: http://auth.local
- **Eureka Dashboard**: http://<MINIKUBE_IP>:8761

## 📊 Monitoring Commands

```bash
# Check deployment status
kubectl get deployments

# Check services
kubectl get services

# Check pods
kubectl get pods

# Check ingress
kubectl get ingress

# Watch pods in real-time
kubectl get pods -w

# View logs for a specific service
kubectl logs -f deployment/gateway

# Check service health
kubectl describe deployment gateway
```

## 🔄 GitOps with Argo CD

To deploy with Argo CD:

1. **Install Argo CD in your cluster**:
   ```bash
   kubectl create namespace argocd
   kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
   ```

2. **Create an Argo CD Application**:
   ```yaml
   apiVersion: argoproj.io/v1alpha1
   kind: Application
   metadata:
     name: ms-esprit
     namespace: argocd
   spec:
     project: default
     source:
       repoURL: <YOUR_GIT_REPO>
       targetRevision: HEAD
       path: k8s
     destination:
       server: https://kubernetes.default.svc
       namespace: default
     syncPolicy:
       automated:
         prune: true
         selfHeal: true
   ```

## 🛠️ Troubleshooting

### Common Issues

1. **Pods not starting**: Check logs with `kubectl logs <pod-name>`
2. **Services not accessible**: Verify ingress controller is running
3. **Database connection issues**: Check ConfigMap and Secret values
4. **Image pull errors**: Ensure images are available in localhost:5000 registry

### Debug Commands
```bash
# Check pod details
kubectl describe pod <pod-name>

# Get pod logs
kubectl logs <pod-name>

# Execute into a pod
kubectl exec -it <pod-name> -- /bin/bash

# Check service endpoints
kubectl get endpoints

# Check ingress details
kubectl describe ingress microservices-ingress
```

## 📈 Scaling

To scale a deployment:
```bash
kubectl scale deployment <deployment-name> --replicas=3
```

## 🧹 Cleanup

To remove all resources:
```bash
kubectl delete -f .
```

## 📝 Notes

- All services are configured with readiness and liveness probes
- Resource limits are set for production readiness
- Secrets are base64 encoded but should be managed securely in production
- The deployment uses ClusterIP services with Ingress for external access
- Configuration follows Kubernetes best practices for microservices deployment
