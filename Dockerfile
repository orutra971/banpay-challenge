FROM eclipse-temurin:21
RUN mkdir /opt/app
COPY target/banpay-challenge-1.0.0.jar /opt/app
CMD ["java", "-jar", "/opt/app/banpay-challenge-1.0.0.jar"]