#!/bin/bash
echo "🧪 Running unit and integration tests..."
mvn test

if [ $? -ne 0 ]; then
    echo "❌ Tests failed. Exiting..."
    exit 1
else
    echo "✅ All tests passed successfully."
fi
