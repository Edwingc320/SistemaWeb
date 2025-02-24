FROM openjdk:17-jdk-alpine
VOLUME /tmp

# Definir el puerto en una variable de entorno
ENV SERVER_PORT=8080

COPY target/sistemaweb-0.0.1-SNAPSHOT.jar /app.jar

# Exponer el puerto correcto (Render lo necesita)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
