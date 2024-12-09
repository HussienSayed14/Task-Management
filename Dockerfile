
FROM amazoncorretto:21-alpine
# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY build/libs/Task_Manager-0.0.1-SNAPSHOT.jar app.jar


ENTRYPOINT ["java", "-jar", "app.jar"]
