FROM adoptopenjdk/openjdk14:alpine-jre

ADD ./build/libs/*.jar app.jar

ADD ./entrypoint.sh entrypoint.sh
RUN ["chmod", "755", "entrypoint.sh"]

ENTRYPOINT ["./entrypoint.sh"]
