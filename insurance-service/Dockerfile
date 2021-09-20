FROM openjdk:11

WORKDIR /opt/insurance-service

COPY ./target/insurance-service-0.0.1-SNAPSHOT.jar ./insurance-service.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom",  "-jar", "./insurance-service.jar"]
