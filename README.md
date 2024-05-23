# maven-central

## Description

Implement a library management system similar to Maven Central. Interaction with the system occurs exclusively via HTTP REST API. Users have the ability to add libraries, versions, and update or delete them.

## Build

To build the project, run the following command:

```bash
mvn clean install
```

## Run

### Database

The project uses MariaDB as a database. To run the project, you need to have a MariaDB server running.
There is a docker-compose file in the root directory that you can use to start a MariaDB server.
You'll see that the application is configured to connect to the database on `localhost:3307` with a password
read from the `MARIADB_ROOT_PASSWORD` environment variable. You can set this variable in the `.env` file
in the root directory so that the docker-compose file can read it.

For integration tests, the application uses an in-memory H2 database.
You can check the application properties files to explore the configuration.


### Application

To run the application, run the following command:

```bash
mvn spring-boot:run
```

### OpenAPI

U can see project Open API specification on link: 

http://localhost:8080/swagger-ui/index.html