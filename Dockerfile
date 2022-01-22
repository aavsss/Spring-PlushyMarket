FROM adoptopenjdk:11-jre-hotspot as builder
RUN mkdir -p /app/source
COPY . /app/source
WORKDIR /app/source
RUN /mvnw clean package

FROM adoptopenjdk:11-jre-hotspot as runtime
COPY --from=builder /app/source/target/*.jar /app/application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/application.jar"]