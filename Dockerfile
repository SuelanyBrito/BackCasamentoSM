# Etapa de build
FROM ubuntu:latest AS build

# Instalar dependências
RUN apt-get update && apt-get install -y \
    wget \
    tar

# Instala o Gradle
ENV GRADLE_VERSION=8.9
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip && \
    unzip gradle-${GRADLE_VERSION}-bin.zip && \
    mv gradle-${GRADLE_VERSION} /opt/gradle && \
    ln -s /opt/gradle/bin/gradle /usr/bin/gradle

# Baixar e instalar o JDK 20
RUN wget https://github.com/adoptium/temurin20-binaries/releases/download/jdk-20+36/OpenJDK20U-jdk_x64_linux_hotspot_20_36.tar.gz \
&& mkdir -p /usr/lib/jvm \
&& tar -xzf OpenJDK20U-jdk_x64_linux_hotspot_20_36.tar.gz -C /usr/lib/jvm \
&& mv /usr/lib/jvm/jdk-20* /usr/lib/jvm/java-20-openjdk \
&& update-alternatives --install /usr/bin/java java /usr/lib/jvm/java-20-openjdk/bin/java 1 \
&& update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/java-20-openjdk/bin/javac 1

# Copiar arquivos de construção
COPY . /app

# Mudar para o diretório de construção
WORKDIR /app

RUN gradle clean
RUN gradle build

RUN ls -l /app/build/libs

# Etapa de runtime
FROM openjdk:20-jdk-slim

# Exponha a porta da aplicação
EXPOSE 8080

# Copie o JAR gerado da etapa de build
COPY --from=build /app/build/libs/app-0.0.1-SNAPSHOT.jar app.jar

# Comando de entrada para iniciar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]