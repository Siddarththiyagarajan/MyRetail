FROM adoptopenjdk/openjdk11:latest

ADD /target/myRetail-0.0.1-SNAPSHOT.jar myRetail.jar
#ADD /src/main/resources/application-dev.properties application.properties

ENTRYPOINT ["java","-jar","/myRetail.jar"]
EXPOSE 8080