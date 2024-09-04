# Etapa de build
FROM ubuntu:latest AS build

# Atualize a lista de pacotes e instale dependências
RUN apt-get update && apt-get install -y wget apt-transport-https

# Adicionar repositório para OpenJDK 22
RUN wget -O- https://apt.corretto.aws/corretto.key | gpg --dearmor | tee /usr/share/keyrings/amazon-corretto-22-keyring.gpg
RUN echo 'deb [signed-by=/usr/share/keyrings/amazon-corretto-22-keyring.gpg] https://apt.corretto.aws stable main' | tee /etc/apt/sources.list.d/corretto-22.list

# Instalar o OpenJDK 22
RUN apt-get update && apt-get install -y openjdk-22-jdk

# Copie o código-fonte para o diretório de trabalho
COPY . .

# Compile o projeto usando Gradle
RUN ./gradlew bootJar --no-daemon

# Etapa de runtime
FROM openjdk:22-jdk-slim

# Exponha a porta da aplicação
EXPOSE 8080

# Copie o JAR gerado da etapa de build
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar

# Comando de entrada para iniciar o aplicativo
ENTRYPOINT ["java", "-jar", "demo.jar"]
