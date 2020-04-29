## General info
REST api for Currency rates project.

## Technologies
Project is created with:
* JDK 1.8
* Gradle 6.3
* SpringBoot 2.2.6.RELEASE(Web, Jpa)
* ModelMapper 2.3.7
* Lombok 1.18.12
* MySQL 8.0.18
	
## Setup
Set application.properties:
spring.datasource.username=/your.mysql.username/
spring.datasource.password=/your.mysql.password/

After starting the server, the database schema will be created automatically 
and the data will be loaded from resources/data.sql file.
