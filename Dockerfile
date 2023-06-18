FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/basarabeascabot-1.0.0.jar .
EXPOSE 5000
ENTRYPOINT ["java","-jar","basarabeascabot-1.0.0.jar"]