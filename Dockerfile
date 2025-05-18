FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copia el jar ya construido desde el host
COPY boot/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]