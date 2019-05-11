FROM openjdk:8-alpine
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN ./gradlew fatJar


FROM openjdk:8-jre-slim
COPY --from=0 /usr/src/app/rest/build/libs/dgo-all.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
