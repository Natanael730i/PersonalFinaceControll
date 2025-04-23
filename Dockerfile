FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar app.jar

# Door exposed
EXPOSE 8090

# Run project
ENTRYPOINT ["java", "-jar", "app.jar"]