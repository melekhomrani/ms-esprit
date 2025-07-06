#!/bin/bash
set -e

CONFIG_SERVER_REPO="https://github.com/melekhomrani/config-server.git"
CONFIG_SERVER_DIR="config-server"

echo "🔄 Checking for existing config-server repository..."

if [ -d "$CONFIG_SERVER_DIR/.git" ]; then
    echo "✅ config-server repository already exists. Pulling latest changes..."
    cd "$CONFIG_SERVER_DIR"
    git pull
    echo "✅ config-server updated successfully."
else
    echo "📥 Cloning config-server repository..."
    git clone "$CONFIG_SERVER_REPO" "$CONFIG_SERVER_DIR"
    echo "✅ config-server cloned successfully."
fi

