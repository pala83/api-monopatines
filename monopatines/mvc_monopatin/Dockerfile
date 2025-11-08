FROM eclipse-temurin:21-jdk-alpine
ARG JAR_FILE=target/mvc_monopatin-0.0.1.jar
WORKDIR /app
COPY ${JAR_FILE} mvc_monopatin.jar
EXPOSE 8084
ENTRYPOINT ["sh","-c","java -jar /app/mvc_monopatin.jar"]
