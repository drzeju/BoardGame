# BoardGame
> Spring Boot restful api to manage boardgame store

## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Setup](#setup)
* [Usage](#usage)


## General Information
- I created this project to better understand the concept of restful applications.
- It is simple backend ecommerce application 


## Technologies Used
- Java - version 11
- Spring Boot - version 2.6.9
- Swagger v2 - version 2.9.2
- MySQL - version 8.0.25


## Features
List the ready features here:
- Spring Security Login
- CRUD operations
- Sending messages to Consumer app


## Setup
- RabbitMQ:
  - port: 15672
  - username: guest
  - password: guest
  
- BoardGame:
  - port: 8080
  
- RabbitMQConsumer:
  - port: 9090
  
- MySQL:
  - port: 3306
  - database: boardgame
  - username: root
  - password: Root
> On start DataLoader is inputing some data into the repositories to allow user to get along with app quicker.

> After running the app open your browser and copy below url address:
- http://localhost:8080/
>This should redirect you to login page.

> Use preload login data listed below to start working with the app:
- username: admin@test.com
- password: admin
  
> If you want to logout to change user for example to test USER role try:
- http://localhost:8080/logout


## Usage
By deafault application DDL setting is set up to 'create' value.
> If you want to change this property go to:
* BoardGame/src/main/resources/application.properties
> and change this line (for example to update or none)
* spring.jpa.hibernate.ddl-auto=create

