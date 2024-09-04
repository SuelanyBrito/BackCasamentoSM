# Etapa de build
FROM ubuntu:latest AS build

# Atualize a lista de pacotes e instale o OpenJDK 17
RUN apt-get update && apt-get install -y openjdk-18-jdk wget unzip

# Copie o código-fonte para o diretório de trabalho
COPY . .

# Garantir que o Gradle Wrapper tenha permissões executáveis
RUN chmod +x ./gradlew

# Compile o projeto usando Gradle
RUN ./gradlew bootJar --no-daemon -Dorg.gradle.java.home=/usr/lib/jvm/java-18-openjdk-amd64 bootJar

# Etapa de runtime
FROM openjdk:18-jdk-slim

# Exponha a porta da aplicação
EXPOSE 8080

# Copie o JAR gerado da etapa de build
COPY --from=build /target/sm-0.0.1-SNAPSHOT.jar sm.jar

# Comando de entrada para iniciar o aplicativo
ENTRYPOINT ["java", "-jar", "sm.jar"]
