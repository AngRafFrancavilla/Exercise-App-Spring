# Usa Java 21 per runtime
FROM eclipse-temurin:21-jdk-jammy

# Imposta la working dir
WORKDIR /app

# Copia il JAR generato da Maven nel container
COPY target/exercise-app-0.0.1-SNAPSHOT.jar app.jar

# Espone la porta 8080
EXPOSE 8080

# Avvia l'app
ENTRYPOINT ["java", "-jar", "app.jar"]
