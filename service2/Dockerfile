FROM adoptopenjdk:11-jre-hotspot
COPY ./target/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]