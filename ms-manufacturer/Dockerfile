# FROM eclipse-temurin:17-jdk-alpine AS mvn-build
# WORKDIR /app/build
# COPY ./src ./src
# COPY pom.xml .
# COPY .mvn .mvn
# COPY mvnw .
# RUN ./mvnw clean install -Dmaven.test.skip=true

# FROM eclipse-temurin:17-jdk-alpine
# WORKDIR /app
# COPY --from=mvn-build /app/build/target/*.jar ./app.jar
# EXPOSE 8081
# CMD ["java", "-jar", "/app/app.jar"]

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY ./target/*.jar ./app.jar
EXPOSE 8082
CMD ["java", "-jar", "/app/app.jar"]