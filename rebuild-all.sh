#!/bin/bash

echo "🚀 Cleaning and building all microservices..."

mvn clean install

echo "✅ Build complete."
touch build_jars.sh deploy_jars.sh build_run_authentication.sh start_dependencies.sh build_run_config_server.sh build_run_eureka_server.sh build_run_gateway.sh build_run_microservices.sh
