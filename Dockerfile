# Etapa de build
FROM ubuntu:latest AS build

# Atualize a lista de pacotes e instale o OpenJDK 19

# Instalar dependências e o OpenJDK 19
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    && wget https://download.java.net/java/GA/jdk19/0d0b1b0d6d0f1b3884f9d36cba040e6d6d31b65b/jdk-19_linux-x64_bin.tar.gz \
    && tar -xzf jdk-19_linux-x64_bin.tar.gz \
    && mv jdk-19 /usr/lib/jvm/java-19-openjdk \
    && update-alternatives --install /usr/bin/java java /usr/lib/jvm/java-19-openjdk/bin/java 1 \
    && update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/java-19-openjdk/bin/javac 1


# Copie o código-fonte para o diretório de trabalho
COPY . .

# Garantir que o Gradle Wrapper tenha permissões executáveis
RUN chmod +x ./gradlew

# Compile o projeto usando Gradle
RUN ./gradlew bootJar --no-daemon -Dorg.gradle.java.home=/usr/lib/jvm/java-19-openjdk-amd64 bootJar

# Etapa de runtime
FROM openjdk:19-jdk-slim

# Exponha a porta da aplicação
EXPOSE 8080

# Copie o JAR gerado da etapa de build
COPY --from=build /app/build/libs/sm-0.0.1-SNAPSHOT.jar sm.jar

# Comando de entrada para iniciar o aplicativo
ENTRYPOINT ["java", "-jar", "sm.jar"]
