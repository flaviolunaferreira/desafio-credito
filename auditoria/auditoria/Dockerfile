# Etapa 1: Construir e instalar o projeto api
FROM maven:3.9.6-eclipse-temurin-17 AS build-api
WORKDIR /app-api
COPY ../../api/api /app-api
RUN mvn clean install -DskipTests

# Etapa 2: Construir o projeto auditoria
FROM maven:3.9.6-eclipse-temurin-17 AS build-auditoria
WORKDIR /app
COPY . /app
COPY --from=build-api /root/.m2/repository /root/.m2/repository
RUN mvn clean package -DskipTests

# Etapa 3: Criar imagem leve apenas com o JAR
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build-auditoria /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]