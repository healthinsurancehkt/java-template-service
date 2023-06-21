# syntax = docker/dockerfile:1.2
FROM maven:3.8-openjdk-17-slim as BUILD
COPY .mvn /.mvn
COPY src /src
COPY pom.xml /
COPY lombok.config /
COPY credentials.json /
RUN mkdir /.m2
WORKDIR /.m2
USER root
RUN --mount=type=cache,target=/root/.m2 export GOOGLE_APPLICATION_CREDENTIALS=/credentials.json && \
    mvn -f /pom.xml -DskipTests=true clean package

FROM openjdk:17-slim
EXPOSE 8080
VOLUME /tmp
COPY --from=BUILD /target/java-template-service-0.0.1-SNAPSHOT.jar service.jar
CMD ["java","-jar","service.jar"]