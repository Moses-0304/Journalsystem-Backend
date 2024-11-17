# Use OpenJDK 17 base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the project's jar file into the container
COPY target/*.jar app.jar

# Expose the port that Spring Boot will run on
EXPOSE 8080

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
