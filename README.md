# Java Sample WEB Payment App

Clean Architecture / Hexagonal Java example project using rest and persistence 

&nbsp;
&nbsp;

Minimum Requirements
--------------------

* Java 11 and Maven 3 are required to build and run the application.

* Docker and DockerCompose can be used to provide a Postgresql database instance that is required to run the project.

* Docker is also required to run integration tests with database interaction. Such tests uses Testcontainers library to provide an isolated PostgreSQL instance to test the application.  

* Bash shell and curl command are optional tools used in the scripts provided in the `/scripts` folder. Such scripts provides a convenient way to make REST requests to the application.

&nbsp;
&nbsp;

Run the application
-------------------

You can execute `docker-compose up -d` to setup a database instance and `mvn spring-boot:run` to run the application.

The following are the principal steps in order test the application locally:

| Resource to execute          | Shell command                                                    |
|------------------------------|------------------------------------------------------------------|
| Application tests            | mvn test                                                         |
| Database instance            | docker-compose up -d                                             |
| Application instance         | mvn spring-boot:run                                              |
| Account creation request     | ./scripts/accounts_post.sh http://127.0.0.1:8080 12345678900     |
| Account retrieve request     | ./scripts/accounts_get.sh http://127.0.0.1:8080 1                |
| Transaction creation request |  ./scripts/transactions_post.sh http://127.0.0.1:8080 1 4 123.45 |

&nbsp;
&nbsp;

Business rules
--------------

* An account contains the client identification
* A transaction is associated with a specific account
* A transaction has a value, a creation date and a type
* A transaction type can be cash, installment, withdrawal, payment
* A transaction with a positive amount must be of payment type
* A transaction with a negative amount cannot be of payment type

&nbsp;
&nbsp;

Database model
--------------

[![Database model](/docs/README.md/db_model.png?raw=true "Database model")](https://app.diagrams.net/#Uhttps://github.com/campisano/java_sample_web_payment_app/blob/master/docs/README.md/db_model.png?raw=true)
