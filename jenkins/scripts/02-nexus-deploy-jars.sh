#!/bin/bash

set -e

echo "Deploying artifacts to Nexus..."

mvn deploy -DskipTests