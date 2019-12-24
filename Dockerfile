FROM openjdk:8-jre-slim
COPY ./rest/build/libs/dgo-all-1.0-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
