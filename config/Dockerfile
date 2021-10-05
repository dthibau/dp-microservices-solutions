FROM openjdk:8-jdk-alpine
VOLUME /tmp

RUN apk add --update \
    curl \
    && rm -rf /var/cache/apk/*
COPY target/*.jar app.jar
ENTRYPOINT ["java","-Xmx128m","-jar","/app.jar"]

EXPOSE 8888
