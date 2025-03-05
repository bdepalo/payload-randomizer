FROM openjdk:17-jdk-slim AS build
EXPOSE 8080

WORKDIR /payload

COPY . .
RUN ./gradlew payload-rest:bootJar

FROM eclipse-temurin:17-jre-alpine

COPY --from=build /payload/payload-rest/build/libs/payload-rest.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]