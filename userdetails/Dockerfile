FROM openjdk:8
EXPOSE 80
ADD target/userdetail-0.0.1-SNAPSHOT.jar user/userdetail.jar
ENTRYPOINT ["java", "-jar", "user/userdetail.jar"]