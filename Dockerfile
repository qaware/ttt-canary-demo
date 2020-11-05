FROM openjdk:15

# Add Tini
ENV TINI_VERSION v0.19.0
ADD https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini /tini
RUN chmod +x /tini
ENTRYPOINT ["/tini", "--"]

ARG APPLICATION_VERSION
ARG APPLICATION_FAILURE_PERCENTAGE

ENV APPLICATION_VERSION=$APPLICATION_VERSION
ENV APPLICATION_FAILURE_PERCENTAGE=$APPLICATION_FAILURE_PERCENTAGE

# Run your program under Tini
CMD ["java", "-Xmx128M", "-XX:+UseSerialGC", "-jar", "/app.jar"]
EXPOSE 8080

COPY target/demo-*.jar /app.jar
