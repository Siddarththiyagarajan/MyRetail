FROM adoptopenjdk/openjdk11:latest

ADD /target/myRetail-0.0.1-SNAPSHOT.jar myRetail.jar

ENTRYPOINT ["java","-jar","/myRetail.jar"]
EXPOSE 8080