FROM eclipse-temurin:21-jdk-alpine
ARG JAR_FILE=target/mvc_facturacion-0.0.1.jar
WORKDIR /app
COPY ${JAR_FILE} mvc_facturacion.jar
EXPOSE 8092
ENTRYPOINT ["sh","-c","java -jar /app/mvc_facturacion.jar"]
