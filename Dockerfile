# Etapa 1: Compilar la app
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Crear imagen final con el JAR
FROM eclipse-temurin:17-jdk-slim
# FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/sistemaweb-0.0.1-SNAPSHOT.jar SistemaWeb.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "SistemaWeb.jar"]
