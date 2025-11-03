# ============================
# STAGE 1 - BUILD CON MAVEN
# ============================
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Carpeta de trabajo donde se construirá el proyecto
WORKDIR /build

# Copiamos el pom.xml primero (para cachear dependencias)
COPY pom.xml .

# Copiamos el código fuente
COPY src ./src

# Compilamos el JAR (sin tests para acelerar)
RUN mvn -B -DskipTests clean package


# ============================
# STAGE 2 - RUNTIME
# ============================
FROM eclipse-temurin:21-jre-jammy

# Directorio de trabajo en la imagen final
WORKDIR /app

# Copiamos el JAR generado desde el contenedor anterior
COPY --from=build /build/target/*.jar app.jar

# Variables de entorno
ENV JAVA_OPTS=""
ENV SPRING_PROFILES_ACTIVE=prod

# Puerto del microservicio (cambiá según el que uses)
ARG SERVICE_PORT=8081
EXPOSE ${SERVICE_PORT}

# Comando de ejecución
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
