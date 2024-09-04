# Etapa de build
FROM ubuntu:latest AS build

# Defina o diretório de trabalho
WORKDIR /app

# Copie o código-fonte para o diretório de trabalho
COPY . .

# Compile o projeto usando Gradle
RUN ./gradlew bootJar --no-daemon

# Etapa de runtime
FROM openjdk:19-jdk-slim

# Defina o diretório de trabalho
WORKDIR /app

# Exponha a porta da aplicação
EXPOSE 8080

# Copie o JAR gerado da etapa de build
COPY --from=build /app/build/libs/sm-0.0.1-SNAPSHOT.jar sm.jar

# Comando de entrada para iniciar o aplicativo
ENTRYPOINT ["java", "-jar", "sm.jar"]
