FROM openjdk:11
ADD target/usertradeservice.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]