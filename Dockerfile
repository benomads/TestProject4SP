FROM maven:3.9.3-eclipse-temurin-20 as builder
WORKDIR /app/app
COPY mvnw pom.xml ./
COPY ./src ./src
RUN mvn clean install -DskipTests

FROM eclipse-temurin:20-jre-jammy
WORKDIR /app/app
EXPOSE 8080
COPY --from=builder /app/app/target/*.jar /app/app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app/app.jar"]