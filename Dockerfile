FROM eclipse-temurin:21
RUN mkdir /opt/app
COPY target/banpay-challenge-1.0.0.jar /opt/app
CMD ["java", "-jar", "/opt/app/banpay-challenge-1.0.0.jar"]

#FROM openjdk:21-jdk-alpine
#MAINTAINER baeldung.com
#COPY target/docker-message-server-1.0.0.jar message-server-1.0.0.jar
#ENTRYPOINT ["java","-jar","/message-server-1.0.0.jar"]