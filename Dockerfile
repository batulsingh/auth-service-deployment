FROM openjdk:11
WORKDIR auth-service/src
ADD ./target/auth-service-1.0.0-SNAPSHOT.jar auth-service.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "auth-service.jar"]
