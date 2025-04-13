FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/notification-service-*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","/app.jar"]