#!/bin/bash
echo "🔍 Running SonarQube analysis..."
mvn sonar:sonar \
    -Dsonar.projectKey=ms-esprit \
    -Dsonar.host.url=http://localhost:9000 \
    -Dsonar.login=$SONAR_TOKEN

if [ $? -ne 0 ]; then
    echo "❌ SonarQube analysis failed. Exiting..."
    exit 1
else
    echo "✅ SonarQube analysis completed successfully."
fi