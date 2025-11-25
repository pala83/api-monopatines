FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY ./target/mvc_asistente-0.0.1.jar /app/app.jar
COPY .env /app/.env
EXPOSE 8092
ENTRYPOINT ["sh", "-c", "export $(cat /app/.env | xargs) && java -jar app.jar"]
