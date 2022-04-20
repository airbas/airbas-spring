FROM openjdk:13-alpine
RUN addgroup -S spring && adduser -S spring -G spring
VOLUME /tmp
EXPOSE 8081
ARG DEPENDENCY=target
ADD ${DEPENDENCY}/*.jar servprof.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/servprof.jar"]
