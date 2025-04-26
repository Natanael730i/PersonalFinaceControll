# Etapa 1: build
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app

# Copia o código e empacota com Maven
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: runtime
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copia o jar gerado
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta (ajuste conforme seu app)
EXPOSE 8080

# Roda o app
ENTRYPOINT ["java", "-jar", "app.jar"]


#FROM openjdk:17-jdk-slim
#
#WORKDIR /app
#
#COPY target/*.jar app.jar
#
## Door exposed
#EXPOSE 8090
#
## Run project
#ENTRYPOINT ["java", "-jar", "app.jar"]