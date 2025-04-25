ENV JAVA_OPTS=$JAVA_OPTS
COPY target/sistemaweb-0.0.1-SNAPSHOT.jar sistemaweb.jar
EXPOSE 5000
ENTRYPOINT exec java $JAVA_OPTS -jar sistemaweb.jar
#  ENTRYPOINT [ "java", "-jar", "sistemaweb.jar" ]
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar sistemaweb.jar
