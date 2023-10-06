# Commands to start the server
1. ./mvnw package ; java -jar target/spring-boot-docker.jar
2. docker-compose up

To setup docker:   https://www.baeldung.com/spring-boot-postgresql-docker

docker rmi docker-spring-boot-postgres:latest

To run postgres server in machine: `brew services start postgresql`