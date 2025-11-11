FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY ./target/mvc_monopatin-0.0.1.jar app.jar
EXPOSE 8084
ENTRYPOINT exec sh -c 'echo "Waiting for db_monopatin:3306..."; \
  until nc -z db_monopatin 3306; do echo "db not ready yet - sleeping 2s"; sleep 2; done; \
  echo "db is up - starting app"; \
  exec java -jar /app/app.jar'