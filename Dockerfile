FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY target/sistemaweb-0.0.1-SNAPSHOT.jar /app.jar

EXPOSE 3000
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar