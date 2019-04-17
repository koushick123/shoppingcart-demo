FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/shoppingcart-demo-1.0.RELEASE.jar app.jar
#ARG JAR_FILE
#COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
