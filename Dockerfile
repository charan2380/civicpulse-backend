# Stage 1: Build the application using a Maven image
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# --- THIS IS THE CORRECTED LINE ---
# Copy the Maven Wrapper configuration, the script, and the project file
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download dependencies to leverage Docker layer caching
RUN ./mvnw dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Package the application into a .jar file
RUN ./mvnw -DskipTests clean package


# Stage 2: Create the final, smaller image to run the application
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built .jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on (Render will map this)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java","-jar","app.jar"]