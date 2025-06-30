#!/bin/bash

# create variable to hold the first argument
CONFIG_SERVER_DIR="$1"

echo "changing directory to $CONFIG_SERVER_DIR"
cd "$CONFIG_SERVER_DIR" || exit 1

echo "Building the jar..."
./mvnw clean package -DskipTests


echo "$CONFIG_SERVER_DIR built successfully."

# Start the config server
echo "Starting $CONFIG_SERVER_DIR Service..."
docker compose up -d --build "$CONFIG_SERVER_DIR"