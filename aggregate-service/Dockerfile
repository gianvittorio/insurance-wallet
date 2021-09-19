FROM openjdk:11

WORKDIR /opt/aggregate-service

COPY ./target/aggregate-service-0.0.1-SNAPSHOT.jar ./aggregate-service.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom",  "-jar", "./aggregate-service.jar"]
