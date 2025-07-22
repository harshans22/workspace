# ----------- Build Stage -----------
FROM gradle:8.8-jdk17-alpine AS build
WORKDIR /app

# Copy only necessary files for dependency caching
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle

RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon

# Copy source code now
COPY src ./src

RUN ./gradlew bootJar --no-daemon

# ----------- Runtime Stage -----------
FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
