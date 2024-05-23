FROM ubuntu:latest
LABEL authors="ivanakrizanec"
WORKDIR /app
COPY target/maven-central-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]