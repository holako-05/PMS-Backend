FROM openjdk:17-jdk-alpine
LABEL maintainer="pms-kafka-project"
ADD target/product_management_system_kafka-0.0.1-SNAPSHOT.jar pms-kafka-docker.jar
ENTRYPOINT ["java","-jar","pms-kafka-docker.jar"]