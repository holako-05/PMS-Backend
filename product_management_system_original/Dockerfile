FROM openjdk:17-jdk-alpine
LABEL maintainer="pms-original-project"
VOLUME /app/images
ADD target/product_management_system_original-0.0.1-SNAPSHOT.jar pms-original-docker.jar
ENTRYPOINT ["java","-jar","pms-original-docker.jar"]