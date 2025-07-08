#!/bin/bash
set -e

echo "🔍 Running SonarQube analysis using sonar-project.properties..."
echo "Using SonarQube URL: $SONAR_HOST_URL"
echo "Using SonarQube Scanner: $SONAR_SCANNER_HOME"

${SONAR_SCANNER_HOME}/bin/sonar-scanner \
    -Dproject.settings=sonar-project.properties

if [ $? -ne 0 ]; then
    echo "❌ SonarQube analysis failed. Exiting..."
    exit 1
else
    echo "✅ SonarQube analysis completed successfully."
fi
