# Etapa de build
FROM ubuntu:latest AS build

# Atualize a lista de pacotes e instale o OpenJDK 17
RUN apt-get update && apt-get install -y openjdk-17-jdk

# Copie o código-fonte para o diretório de trabalho
COPY . .

# Compile o projeto usando Gradle
RUN ./gradlew bootJar --no-daemon

# Etapa de runtime
FROM openjdk:17-jdk-slim

# Exponha a porta da aplicação
EXPOSE 8080

# Copie o JAR gerado da etapa de build
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar

# Comando de entrada para iniciar o aplicativo
ENTRYPOINT ["java", "-jar", "demo.jar"]
