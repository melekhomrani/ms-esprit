#!/bin/bash

set -e

echo "Building all microservices JARs..."

mvn clean install -DskipTests
