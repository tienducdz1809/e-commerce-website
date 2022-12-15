# Back-end build for ecommerce system

## Purpose
This project is for building an [REST API](https://200lab.io/blog/rest-api-la-gi-cach-thiet-ke-rest-api/) web hosting with CRUD function and deal with MYSQL database.

## Require
- MySQL database, such as:
  - Xampp
  - MySQL Workbench
- IDEA can run maven project, such as: 
  - Eclipse IDE for Java EE Developers (have Spring Tool suite)
  - NetBeans
  - IntelliJ IDEA (ultimate version)
- Git

## Quick start
### Use with terminal
Ensure [requirements](##Require) are met, clone the repository or download zip folder and unzip, then import to your IDEA

Connect to your database and create a schema with name "e_com"

Open the  [application.properties](./src/main/resources/application.properties) in the resources package.

Replace:
- **_$your-schema_** with the name of your schema you had created in the previous step.
- **_$your-schema_** with your database username.
- **_$your-schema_** with your password of the database.

````
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/$your-schema
spring.datasource.username=$your-username
spring.datasource.password=$your-password
````

:relaxed: After that, run the maven project and everything is done. 

**Testing the api**: We also have a json collection file in our project folder, just import to [Postman](https://www.postman.com/downloads/) and test as normal. 

**Note**: The project will automatically fill with an amount of data that we have prepared. Therefore, you are no need to run any .sql file in your database.

