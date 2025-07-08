#!/bin/bash
set -e
echo "Deploying microservices JARs to Nexus..."
mvn deploy -DskipTests
