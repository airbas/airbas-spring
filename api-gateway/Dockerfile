FROM openjdk:13-alpine
RUN addgroup -S spring && adduser -S spring -G spring
VOLUME /tmp
EXPOSE 8084
ARG DEPENDENCY=target
ADD ${DEPENDENCY}/*.jar servgate.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/servgate.jar"]
