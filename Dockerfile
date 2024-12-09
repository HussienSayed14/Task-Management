
FROM amazoncorretto:21-alpine
# Set the working directory inside the container
WORKDIR /app

# Install timezone-related packages and configure the timezone
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Africa/Cairo /etc/localtime && \
    echo "Africa/Cairo" > /etc/timezone && \
    apk del tzdata

# Copy the built JAR file into the container
COPY build/libs/Task_Manager-0.0.1-SNAPSHOT.jar app.jar

# Set the timezone environment variable
ENV TZ=Africa/Cairo


ENTRYPOINT ["java", "-jar", "app.jar"]
