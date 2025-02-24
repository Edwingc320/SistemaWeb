FROM amazoncorretto:17
FROM maven:3.8.1-jdk-17
COPY target/sistemaweb-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT [ "java", "-jar", "/app.jar" ]   