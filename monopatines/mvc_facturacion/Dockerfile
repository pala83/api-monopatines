FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY ./target/mvc_facturacion-0.0.1.jar /app/app.jar
EXPOSE 8088
ENTRYPOINT sh -c 'echo "Waiting for db_facturacion:3306..."; \
  until nc -z db_facturacion 3306; do echo "db not ready yet - sleeping 2s"; sleep 2; done; \
  echo "db is up - starting app"; \
  exec java -jar /app/app.jar'
