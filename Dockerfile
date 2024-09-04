# Etapa de build
FROM ubuntu:latest AS build

# Instalar dependências
RUN apt-get update && apt-get install -y \
    wget \
    tar

# Baixar e instalar o OpenJDK 19
RUN wget https://github.com/adoptium/temurin19-binaries/releases/download/jdk-19+36/OpenJDK19U-jdk_x64_linux_hotspot_19_36.tar.gz \
    && mkdir -p /usr/lib/jvm \
    && tar -xzf OpenJDK19U-jdk_x64_linux_hotspot_19_36.tar.gz -C /usr/lib/jvm \
    && mv /usr/lib/jvm/jdk-19* /usr/lib/jvm/java-19-openjdk \
    && update-alternatives --install /usr/bin/java java /usr/lib/jvm/java-19-openjdk/bin/java 1 \
    && update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/java-19-openjdk/bin/javac 1

# Copie o código-fonte para o diretório de trabalho
COPY . .

# Defina o diretório de trabalho
WORKDIR /app

# Garantir que o Gradle Wrapper tenha permissões executáveis
RUN chmod +x ./gradlew

# Compile o projeto usando Gradle
RUN ./gradlew bootJar --no-daemon -Dorg.gradle.java.home=/usr/lib/jvm/java-19-openjdk bootJar

# Etapa de runtime
FROM openjdk:19-jdk-slim

# Exponha a porta da aplicação
EXPOSE 8080

# Copie o JAR gerado da etapa de build
COPY --from=build /app/build/libs/sm-0.0.1-SNAPSHOT.jar sm.jar

# Comando de entrada para iniciar o aplicativo
ENTRYPOINT ["java", "-jar", "sm.jar"]