FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY target/api_gateway-0.0.1.jar app.jar
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/app.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
