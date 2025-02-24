
# Usar la imagen oficial de OpenJDK
FROM openjdk:17-jdk-slim

# Copiar el archivo JAR de la aplicación a la imagen
COPY target/sistemaweb-0.0.1-SNAPSHOT.jar SistemaWeb.jar

# Exponer el puerto 8080
EXPOSE 8083

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "SistemaWeb.jar"]
