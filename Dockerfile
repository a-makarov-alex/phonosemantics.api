FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8083
ARG JAR_FILE=target/API_phonosemantics-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} test-backend.jar
ENTRYPOINT ["java","-jar","/test-backend.jar"]