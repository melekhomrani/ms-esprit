#!/bin/bash
set -e
echo "Starting databases, Zookeeper, Kafka, and Keycloak with realm import..."
docker-compose up -d keycloakdb
docker-compose up -d productdb
docker-compose up -d manufacturerdb
docker-compose up -d zookeeper
docker-compose up -d kafka
docker-compose up -d keycloak
