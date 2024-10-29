FROM eclipse-temurin:17.0.10_7-jdk-jammy as build
WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

COPY src src
RUN chmod +x ./gradlew && \
    ./gradlew clean && \
    ./gradlew build --exclude-task test

RUN rm -rf /root/.gradle

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /workspace/app/build/libs/*.jar app.jar
COPY --from=build /workspace/app/build/agent/*.jar opentelemetry-javaagent.jar

ENTRYPOINT [ \
    "java", "-javaagent:/app/opentelemetry-javaagent.jar",\
    "-jar",\
    "app.jar"]