FROM openjdk:11

WORKDIR /opt/b2b-service

COPY ./target/b2b-service-0.0.1-SNAPSHOT.jar ./b2b-service.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "./b2b-service.jar"]
