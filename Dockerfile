# Etapa de build
FROM ubuntu:latest AS build

# Atualize a lista de pacotes e instale o OpenJDK 22
RUN apt-get update && apt-get install -y openjdk-22-jdk wget unzip

# Defina o diretório de trabalho
WORKDIR /app

# Copie o código-fonte para o diretório de trabalho
COPY . .

# Garantir que o Gradle Wrapper tenha permissões executáveis
RUN chmod +x ./gradlew

# Configurar a toolchain do Gradle para usar o JDK 22
RUN ./gradlew --no-daemon -Dorg.gradle.java.home=/usr/lib/jvm/java-22-openjdk-amd64 bootJar

# Etapa de runtime
FROM openjdk:22-jdk-slim

# Exponha a porta da aplicação
EXPOSE 8080

# Copie o JAR gerado da etapa de build
COPY --from=build /app/build/libs/demo-0.0.1-SNAPSHOT.jar demo.jar

# Comando de entrada para iniciar o aplicativo
ENTRYPOINT ["java", "-jar", "demo.jar"]
