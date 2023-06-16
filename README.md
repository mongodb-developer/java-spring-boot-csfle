# Quick Start Java Spring Boot & MongoDB Atlas Project with Automatic CSFLE(Client Side Field Level Encryption)
This is a demo springboot application created using IntelliJ Spring Initialzr which will show case basic CRUD and Automatic CSFLE(Client Side Field Level Encryption with MongoDB Atlas
to show how to connect to Atlas using a Java Spring Boot Application with PII data automatically encrypted before sending it to the database, that is, MongoDB Atla

## Supported versions:

- Java 11
- Spring boot 2.7.10
- Spring Data MongoDB
- MongoDB Atlas
- Maven 

## MongoDB Atlas

- Get started with a Free Tier Cluster on [MongoDB Atlas](https://www.mongodb.com/cloud/atlas).
- Read this blog post: [Quick Start - Getting your Free MongoDB Atlas Cluster](https://developer.mongodb.com/quickstart/free-atlas-cluster).
- You will need to add mongodb.properties file and add the default MongoDB URI `spring.data.mongodb.uri`, database `spring.data.mongodb.database` and the collection `spring.data.mongodb.collection` in the file.

## How To Run

Replace the Atlas URI in application.properties and run as a spring boot application.

## Commands

- Start the server in a console with `mvn spring-boot:run`.
- If you add some Unit Tests, you would start them with `mvn clean test`.
- You can build the project with `mvn clean package`.

## Disclaimer
Use at your own risk; not a supported MongoDB product

## Author
- Megha Arora @ MongoDB.
