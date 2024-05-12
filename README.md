# Studio Ghibli API Consuming Application

This application is a Java application designed to consume and work with the Studio Ghibli API.

## Features

- Retrieves data from the Studio Ghibli API about films, people, vehicles, species and locations.
- Utilizes Spring HTTP libraries to integrate with the API.
- Has classes to encapsulate each of these entities: Films, People, Vehicles, Species, and Locations.


## Prerequisites

Before you begin, ensure you have installed:
* [Java SDK version 21](https://jdk.java.net/21/)
* [Maven](https://maven.apache.org/download.cgi)
* [Docker](https://www.docker.com/products/docker-desktop/)

## Setting Up

Here are the steps to set up the development environment:

1. Clone the repository:

```bash
git clone https://github.com/orutra971/banpay-challenge
```

2. Navigate into the project directory:

```bash
cd banpay-challenge
```

3. Install the dependencies:

```bash
mvn clean install
```

## Running the Application

To run the application, use the command:

```bash
mvn spring-boot:run
```
## Running for Production

To run the application in production we have to create the package and create a image with docker

1. Create the package of the application
    ```bash
    mvn clean install package -Pprod
    ```
2. Build the docker image
    ```bash
    docker build --tag=banpay-challenge:latest .
    ```
3. Run the application via ```docker-compose```
    ```bash
    docker-compose -f docker-compose.prod.yml up
    ```



## Built With

* [Java SDK version 21](https://jdk.java.net/21/)
* [Jakarta EE](https://jakarta.ee/)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [Spring MVC](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
* [Maven](https://maven.apache.org/)

## Authors

* Arturo López López
