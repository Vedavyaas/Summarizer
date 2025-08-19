# ---- Stage 1: Build ----
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only pom.xml first for dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src src

# Build the app (skip tests for faster build)
RUN mvn clean package -DskipTests

# ---- Stage 2: Run ----
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy only the built jar from previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose custom port
EXPOSE 8001

# Default port for the container
ENV PORT=8001

# Run the app on port 8001
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}"]

