FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /build
COPY src/ ./src
COPY pom.xml ./
RUN mvn package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /build/target/pizzeria-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "pizzeria-0.0.1-SNAPSHOT.jar"]