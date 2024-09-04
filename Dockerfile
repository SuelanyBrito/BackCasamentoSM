FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-22-jdk -y
COPY . .

RUN ./gradlew bootJar --no-daemon

FROM openjdk:22-jdk-slim

EXPOSE 8080

COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar

ENTRYPOINT ["java", "-jar", "demo.jar"]