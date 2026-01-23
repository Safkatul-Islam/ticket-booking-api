# 1. Start with a base Operating System that has Java 17 installed
FROM eclipse-temurin:17-jre-alpine

# 2. Add a temporary volume for storing files (optional but good practice for Tomcat)
VOLUME /tmp

# 3. Copy our compiled JAR file from the target folder into the container
# Format: COPY <source_on_laptop> <destination_inside_container>
COPY target/ticket-booking-api-0.0.1-SNAPSHOT.jar app.jar

# 4. Expose port 8080 (Just documentation, doesn't actually open the port)
EXPOSE 8080

# 5. The command to run when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]