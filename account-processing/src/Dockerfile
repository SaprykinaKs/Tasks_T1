FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml .
COPY account-processing/pom.xml ./account-processing/pom.xml

RUN mvn -f account-processing/pom.xml dependency:go-offline -DskipTests

COPY . .
RUN mvn -f account-processing/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/account-processing/target/account-processing-1.0.0.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]
