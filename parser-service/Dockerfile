FROM public.ecr.aws/maven/maven:3.9.3-temurin-17 AS build
WORKDIR /app

# copy everything
COPY pom.xml ./
COPY parser-service ./parser-service
COPY processor-service ./processor-service

# build parser-service
WORKDIR /app/parser-service
RUN mvn clean package -DskipTests

FROM public.ecr.aws/temurin/17-jdk AS runtime
WORKDIR /app
COPY --from=build /app/parser-service/target/parser-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
