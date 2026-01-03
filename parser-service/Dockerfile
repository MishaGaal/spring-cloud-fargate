FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

# Copy parent POM
COPY pom.xml ./

# Copy service folders
COPY parser-service ./parser-service
COPY processor-service ./processor-service

# Build parser-service
WORKDIR /app/parser-service
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/parser-service/target/parser-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
