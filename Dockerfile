FROM maven:3.5-jdk-8-alpine as BUILD

COPY src /usr/src/myapp/src
COPY pom.xml /usr/src/myapp
RUN mvn  -f /usr/src/myapp/pom.xml clean package

FROM openjdk:8-alpine
VOLUME /tmp
COPY --from=BUILD /usr/src/myapp/target/play.jar /play.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/play.jar"]