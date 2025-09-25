FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml .
COPY credit-processing/pom.xml ./credit-processing/pom.xml

RUN mvn -f credit-processing/pom.xml dependency:go-offline -DskipTests

COPY . .
RUN mvn -f credit-processing/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/credit-processing/target/credit-processing-1.0.0.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","/app/app.jar"]
