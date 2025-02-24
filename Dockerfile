FROM openjdk:17-jdk-alpine



COPY target/sistemaweb-0.0.1-SNAPSHOT /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
 