#!/bin/bash

services=("ms-product" "gateway" "eureka-server" "ms-manufacturer")

for service in "${services[@]}"; do
    (
        echo "👀 Watching $service for changes..."
        cd "$service" || exit 1
        find ./src/main/java -type f | entr -r ./mvnw compile
    ) &
done

wait
