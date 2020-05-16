FROM adoptopenjdk/openjdk14:alpine-jre

ADD ./build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
