FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/shoppingcart-demo-1.0.RELEASE.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","app.jar"]
