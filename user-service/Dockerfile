FROM maven:3.9.4-eclipse-temurin-21-alpine

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Fix: Add execute permission
RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]
