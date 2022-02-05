FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/static/initialData.txt initialData.txt
ENTRYPOINT ["java","-jar","/app.jar"]
