FROM eclipse-temurin:21-jdk-alpine
ARG JAR_FILE=target/mvc_administracion-0.0.1.jar
WORKDIR /app
COPY ${JAR_FILE} mvc_administracion.jar
EXPOSE 8090
ENTRYPOINT ["sh","-c","java -jar /app/mvc_administracion.jar"]
