# pokemon_challenge
# Tecnical test of spring boot microservices

> Attending the Pokemon Challenge 
> The project is developed using microservices architecture using spring boot and spring cloud.

## Content
This repository contains the source code of three instrumentalization microservices: 
Microservice | Description
| :--- | :---
gateway     | Implements the api gateway design pattern and fullfil the role of authorization service with JWT.
eureka	    | Implements the discovery service design pattern.
security    | Implements authentication service with JWT.

And three business microservices: 
Microservice | Description
| :--- | :---
team-manager | Manage the team domail.
trainer      | Manage the trainer profile domain.
catalog	     | Manage the pokemon catalogue.

Each microservice is managed with gradle.

All dependencies are described in the gradle configuration file build.gradle

The microservices database is self-contained, when it starts it creates an in-memory H2 database.

## Requirements 
* java 11 o superior
* git 
* IDE de desarrollo

## Running the microservices
1. Download as zip or clone the repository using git or any graphical wizard.
2. Unzip the downloaded package if you download as zip.
3. Once downloaded you could run the command ./gradlew bootRun in a terminal window in the complete directory of each microservice,
   or run the project if you open it in your favorite IDE

