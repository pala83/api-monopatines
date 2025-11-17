FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY ./target/mvc_asistente-0.0.1.jar /app/app.jar
EXPOSE 8092
ENTRYPOINT ["java","-jar","app.jar"]
