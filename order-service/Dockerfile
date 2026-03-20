FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY order-service/target/order-service-*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java","-jar","app.jar"]