# Quick Start Java Spring Boot & MongoDB Client Side Field Level Encryption

This is a demo springboot application created using IntelliJ Spring Initialzr which will show case basic CRUD and Automatic CSFLE (Client Side Field Level Encryption) with [MongoDB Atlas](https://www.mongodb.com/atlas/database).

This demo connects to [MongoDB Atlas](https://www.mongodb.com/atlas/database) using a [Java Spring Boot](https://spring.io/projects/spring-boot) Application with [Personal Identifiable Information](https://en.wikipedia.org/wiki/Personal_data) (PII) data automatically encrypted before sending it to the database.

## Supported versions:

- Java 11
- Spring boot 2.7.10
- Spring Data MongoDB
- MongoDB Atlas
- Maven

## MongoDB Atlas

- Get started with a Free Tier Cluster on [MongoDB Atlas](https://www.mongodb.com/cloud/atlas).
- Read this blog post: [Quick Start - Getting your Free MongoDB Atlas Cluster](https://developer.mongodb.com/quickstart/free-atlas-cluster).

## How To Run

- Create a file `src/main/resources/mongodb.properties` and add the following entries:
    - MongoDB URI `spring.data.mongodb.uri`
    - database `spring.data.mongodb.database`
    - collection `spring.data.mongodb.collection`
    - path to your mongo_crypt_shared library `crypt.shared.lib.path`

Then you can run as a Spring Boot Application or just run the class `JavaSpringBootCSFLEApplication.java`.

## Commands

- Start the server in a console with `mvn spring-boot:run`.
- If you add some Unit Tests, you would start them with `mvn clean test`.
- You can build the project with `mvn clean package`.

## Disclaimer
Use at your own risk; not a supported MongoDB product.

## Author
- Megha Arora @ MongoDB.
