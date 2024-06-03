# Establece la imagen base
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo jar de la aplicación en el directorio de trabajo
COPY build/libs/order-management-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto que la aplicación Spring Boot está usando
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
