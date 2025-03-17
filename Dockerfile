FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app
COPY . . 

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

FROM openjdk:17-slim

WORKDIR /app

COPY --from=build /app/target/TODO-3.4.3.jar /app/TODO-3.4.3.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/TODO-3.4.3.jar"]
