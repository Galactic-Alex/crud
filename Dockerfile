FROM eclipse-temurin:17-jdk-alpine AS build
RUN mkdir src
WORKDIR /src
COPY . /src
RUN apk add --no-cache maven
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
COPY --from=build /src/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080