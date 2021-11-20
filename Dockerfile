FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080
ADD target/crud-postcodelotterie.jar crud-postcodelotterie-assessment.jar
ENTRYPOINT ["java","-jar","/crud-postcodelotterie-assessment.jar"]