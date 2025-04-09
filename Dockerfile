FROM eclipse-temurin:17-jdk-focal AS builder
WORKDIR /opt/app
COPY . .
RUN apt-get update && apt-get install -y maven \
      && mvn clean package -DskipTests
FROM eclipse-temurin:17-jre-alpine
WORKDIR /opt/app
COPY --from=builder /opt/app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
    