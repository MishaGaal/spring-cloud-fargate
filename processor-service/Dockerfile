FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml ./
COPY processor-service/pom.xml ./processor-service/
COPY processor-service/src ./processor-service/src

WORKDIR /app/processor-service

RUN mvn clean package -DskipTests

# 2️⃣ Runtime stage
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/processor-service/target/processor-service-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Command to run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]