FROM openjdk:17
EXPOSE 8080
ADD target/auto_repair.jar auto_repair.jar
ENTRYPOINT ["java", "-jar", "/auto_repair.jar"]
