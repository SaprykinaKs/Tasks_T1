FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml .
COPY client-processing/pom.xml ./client-processing/pom.xml

RUN mvn -f client-processing/pom.xml dependency:go-offline -DskipTests

COPY . .
RUN mvn -f client-processing/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/client-processing/target/client-processing-1.0.0.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
