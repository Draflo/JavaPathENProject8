FROM gradle:4.8.1-jdk8-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew build --no-daemon

FROM openjdk:8-jre-slim

EXPOSE 8883

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/trippricer.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/trippricer.jar"]