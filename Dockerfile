FROM birdy/graalvm:latest

WORKDIR /tmp/build
ENV GRADLE_USER_HOME /tmp/build/.gradle

ADD . /tmp/build
RUN ./gradlew fatJar
RUN native-image -jar /tmp/build/rest/build/libs/rest-all-1.0-SNAPSHOT.jar -H:+JNI -H:ReflectionConfigurationFiles=reflection.json -H:Name=graal-javalin --static

FROM scratch
COPY --from=0 /tmp/build/graal-javalin /
ENTRYPOINT ["/graal-javalin"]
