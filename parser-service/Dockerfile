# Stage 1: build
FROM amazoncorretto:17 AS build

# Install Maven
RUN yum install -y maven && yum clean all

WORKDIR /app

# Copy the parser-service code
COPY pom.xml ./
COPY parser-service ./parser-service

WORKDIR /app/parser-service

# Build the service
RUN mvn clean package -DskipTests

# Stage 2: runtime image
FROM amazoncorretto:17

WORKDIR /app

# Copy built jar
COPY --from=build /app/parser-service/target/parser-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
