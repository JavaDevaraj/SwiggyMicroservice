FROM adoptopenjdk/openjdk11

ADD target/restaurant-partner-api-service-1.0.jar app.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","app.jar"]

EXPOSE 7070
