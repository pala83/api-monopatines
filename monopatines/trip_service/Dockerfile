FROM eclipse-temurin:21-jdk-alpine
ARG JAR_FILE=target/mvc_usuario-0.0.1.jar
WORKDIR /app
COPY ${JAR_FILE} trip_service.jar
EXPOSE 8086
ENTRYPOINT ["sh","-c","java -jar /app/trip_service.jar"]
