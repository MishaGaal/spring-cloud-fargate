# Stage 1: build
FROM amazoncorretto:17 AS build

# Install Maven
RUN yum install -y maven && yum clean all

WORKDIR /app

# Copy the whole repo
COPY pom.xml ./
COPY parser-service ./parser-service
COPY processor-service ./processor-service

# Build processor-service (or processor-service if needed)
WORKDIR /app/processor-service
RUN mvn clean package -DskipTests

# Stage 2: runtime image
FROM amazoncorretto:17

WORKDIR /app

COPY --from=build /app/processor-service/target/processor-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
