FROM amazoncorretto:11
#COPY target/*-jar-with-dependencies.jar app.jar
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
