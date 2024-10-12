FROM eclipse-temurin:17.0.10_7-jdk-jammy as build
WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

COPY src src

RUN chmod +x ./gradlew && \
    ./gradlew build --exclude-task test

RUN rm -rf /root/.gradle


FROM amazoncorretto:17-alpine3.19-jdk
WORKDIR /app

COPY --from=build /workspace/app/build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]