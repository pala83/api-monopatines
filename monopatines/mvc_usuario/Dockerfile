FROM eclipse-temurin:21-jdk-alpine
ARG JAR_FILE=target/mvc_usuario-0.0.1.jar
WORKDIR /app
COPY ${JAR_FILE} mvc_usuario.jar
EXPOSE 8096
ENTRYPOINT ["sh","-c","java -jar /app/mvc_usuario.jar"]
