FROM openjdk:11
ADD build/libs/app.jar .
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080
