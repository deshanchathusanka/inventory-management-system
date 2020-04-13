FROM openjdk:8-jdk-alpine
ADD build/libs/inventory-application-0.0.1-SNAPSHOT.jar inventory-application-0.0.1-SNAPSHOT.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","inventory-application-0.0.1-SNAPSHOT.jar"]