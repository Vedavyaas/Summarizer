# Use official Java 17 JDK
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and project files
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Copy source code
COPY src src

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build the app
RUN ./mvnw clean package -DskipTests

# Copy the JAR to a standard location
RUN cp target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Environment variable for Render
ENV PORT=8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]

