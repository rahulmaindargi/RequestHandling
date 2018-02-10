FROM openjdk:8
EXPOSE 80
ADD target/pdf_generation-0.0.1-SNAPSHOT.jar pdf/pdf_generation.jar
ENTRYPOINT ["java", "-jar", "pdf/pdf_generation.jar"]