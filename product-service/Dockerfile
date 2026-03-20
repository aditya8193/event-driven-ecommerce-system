FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY product-service/target/product-service-*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","app.jar"]