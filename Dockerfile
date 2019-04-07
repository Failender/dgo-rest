FROM birdy/graalvm:latest

WORKDIR /tmp/build
ENV GRADLE_USER_HOME /tmp/build/.gradle

ADD . /tmp/build
RUN ./gradlew fatJar
RUN native-image -jar /tmp/build/rest/build/libs/rest-all-1.0-SNAPSHOT.jar -H:+JNI -H:ReflectionConfigurationFiles=reflection.json -H:Name=graal-javalin --static --delay-class-initialization-to-runtime=org.hibernate.secure.spi.JaccIntegrator,org.hibernate.dialect.OracleTypesHelper,de.failender.dgo.persistance.HibernateUtil,de.failender.dgo.rest.DgoRest

FROM scratch
COPY --from=0 /tmp/build/graal-javalin /
ENTRYPOINT ["/graal-javalin", "args"]
