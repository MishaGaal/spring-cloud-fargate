# Stage 1: build
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app


COPY pom.xml ./
COPY src ./src

# Build the project
RUN mvn clean package -DskipTests

# Stage 2: runtime image
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy built jar from previous stage
COPY --from=build /app/target/processor-service-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
