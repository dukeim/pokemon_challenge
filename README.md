# pokemon_challenge
# Tecnical test of spring boot microservices

> Attending the Pokemon Challenge 
> The project is developed using microservices architecture using spring boot and spring cloud.

## Content
This repository contains the source code of three instrumentalization microservices: 
Microservice | Port | Description
| :--- | :---: | :---
gateway*     | 9090 | Implements the api gateway design pattern and fullfil the role of authorization service with JWT.
eureka	    |  8099 | Implements the discovery service design pattern.
security*    | 9092 | Implements authentication service with JWT.

'* ***Requires public and private RSA Certificates in C:/RSACertificates. For testing you can copy the ones in the resources folder.***

And three business microservices: 
Microservice | Port | Description
| :--- | :---: | :---
trainer      | 8090 | Manage the trainer profile domain.
catalog	    | 8091 | Manage the pokemon catalogue.
team-manager | 8092 | Manage the team domail.

Each microservice is managed with gradle.

All dependencies are described in the gradle configuration file build.gradle

The microservices database is self-contained, when it starts it creates an in-memory H2 database.

## Requirements 
* java 11 or higher
* git 

## Running the microservices
1. Download as zip or clone the repository using git or any graphical wizard.
2. Unzip the downloaded package if you download as zip.
3. Once downloaded you could run the command ./gradlew bootRun in a terminal window in the complete directory of each microservice,
   or run the project if you open it in your favorite IDE

