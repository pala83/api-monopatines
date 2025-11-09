FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY ./target/mvc_usuario-0.0.1.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]