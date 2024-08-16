FROM openjdk:17-ea-11-jdk-slim
COPY build/libs/gpt-be-0.0.1-SNAPSHOT.jar app.jar
ENV TZ Asia/Seoul
ENTRYPOINT ["java", "-jar", "app.jar"]