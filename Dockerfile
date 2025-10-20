# Start from a Java runtime if it's a Spring Boot app
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy your JAR
COPY target/employee-producer-service-0.0.1.jar app.jar

# Expose port (Cloud Run uses 8080 by default)
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java","-jar","app.jar"]
