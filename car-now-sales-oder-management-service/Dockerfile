FROM adoptopenjdk/openjdk11:latest as builder
RUN mkdir -p /app/src
COPY . /app/src
WORKDIR /app/src
RUN chmod +x ./mvnw
RUN ./mvnw clean package
FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=target/*.jar
COPY --from=builder /app/src/${JAR_FILE} application.jar
ENTRYPOINT ["java","-jar","/application.jar"]
