#!/bin/bash
set -e

echo "🔄 Cloning public config-server repo..."

git clone https://github.com/melekhomrani/config-server.git config-server

echo "✅ Config-server cloned inside $(pwd)/config-server"
