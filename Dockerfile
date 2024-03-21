FROM eclipse-temurin:17-jdk-alpine
RUN mkdir src
WORKDIR /src
COPY . /src
RUN apk add --no-cache maven
RUN mvn clean package -DskipTests

COPY /src/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080