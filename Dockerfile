FROM docker.io/library/gradle:7.5-jdk17 AS build

WORKDIR /app

ADD . /app/

RUN gradle --no-daemon clean bootJar

###

FROM docker.io/library/eclipse-temurin:17-jdk AS app

WORKDIR /opt/server
COPY --from=build /app/build/libs/example-quartz-0.0.1-SNAPSHOT.jar /opt/server

EXPOSE 8080

# server run
ENTRYPOINT ["java"]
CMD ["-jar", "/opt/server/example-quartz-0.0.1-SNAPSHOT.jar"]
