#!/bin/bash
set -e

echo "🔄 Adding ssh key"

ssh-add ~/.ssh/github_personal/id_rsa

echo "🔄 Cloning public config-server repo..."

git clone git@github.com:melekhomrani/config-server.git config-server

echo "✅ Config-server cloned inside $(pwd)/config-server"
