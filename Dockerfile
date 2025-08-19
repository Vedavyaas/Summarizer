# ---- Stage 1: Build ----
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml from Backend-Summarizer
COPY Backend-Summarizer/pom.xml .

# Pre-download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY Backend-Summarizer/src src

# Build the app (skip tests for faster build)
RUN mvn clean package -DskipTests

# ---- Stage 2: Run ----
FROM mcr.microsoft.com/openjdk/jdk:17-ubuntu
WORKDIR /app

# Copy only the built jar from previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose custom port
EXPOSE 8001

# Default port for the container
ENV PORT=8001

# Run the app on port 8001
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}"]

