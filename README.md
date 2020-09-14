[![Build Status](https://travis-ci.com/campisano/java_sample_web_payment_app.svg?branch=master "Build Status")](https://travis-ci.com/campisano/java_sample_web_payment_app)
[![Test Coverage](https://codecov.io/gh/campisano/java_sample_web_payment_app/branch/master/graph/badge.svg "Test Coverage")](https://codecov.io/gh/campisano/java_sample_web_payment_app)
[![Code Quality](https://img.shields.io/lgtm/grade/java/g/campisano/java_sample_web_payment_app.svg "Code Quality")](https://lgtm.com/projects/g/campisano/java_sample_web_payment_app/context:java)
[![Docker Hub](https://img.shields.io/docker/image-size/riccardocampisano/public/java_sample_web_payment_app-latest?label=java_sample_web_payment_app-latest&logo=docker)](https://hub.docker.com/r/riccardocampisano/public/tags?name=java_sample_web_payment_app)

# Java Sample WEB Payment App

This is a Java 14 / SpringBoot sample REST project developed following [Test-Driven Development](https://en.wikipedia.org/wiki/Test-driven_development) in an [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) / [Hexagonal](https://web.archive.org/web/20180822100852/http://alistair.cockburn.us/Hexagonal+architecture) style.

&nbsp;
&nbsp;

Project structure
-----------------

The project is structured in the following folders:

```
root
├── ci/                         (continuous integration files)
├── docs/                       (docs files)
├── scripts/                    (utility scripts to test REST requests)
├── src/                        (source files)
│   ├── main/*/                     (breath of fantasy source files)
│   │   ├── adapters/                   (adapters with framework/infra stuffs)
│   │   ├── application/                (application logic)
│   │   ├── domain/                     (business classes)
│   │   └── Application.java            (main source file)
│   │
│   └── test/                       (test files)
│
├── targets/                    (compiled files)
├── Makefile                    (make file used to simplify build process)
└── pom.xml                     (maven file defining build directives)
```

&nbsp;
&nbsp;

Minimum System Requirements
---------------------------

* Java 14 and Maven 3 are required to build and run the application.

* Docker and DockerCompose can be used to provide a Postgresql database instance that is required to run the project.

* Docker is also required to run integration tests with database interaction. Such tests uses Testcontainers library to provide an isolated PostgreSQL instance to test the application.

* Bash shell and curl command are optional tools used in the scripts provided in the `/scripts` folder. Such scripts provides a convenient way to make REST requests to the application.

&nbsp;
&nbsp;

Build and Run
-------------

You can execute `docker-compose up -d` to setup a database instance and `mvn spring-boot:run` to run the application.

The following commands can be used to test the application locally:

| Resource to execute          | Command                                                         |
|------------------------------|-----------------------------------------------------------------|
| Application tests            | mvn test                                                        |
| Database instance            | docker-compose up -d                                            |
| Application instance         | mvn spring-boot:run                                             |
| Account creation request     | ./scripts/accounts_post.sh http://127.0.0.1:8080 12345678900    |
| Account retrieve request     | ./scripts/accounts_get.sh http://127.0.0.1:8080 1               |
| Transaction creation request | ./scripts/transactions_post.sh http://127.0.0.1:8080 1 4 123.45 |

&nbsp;
&nbsp;

Business rules used for TDD
---------------------------

* An Account contains the client identification and define a credit limit.
* An Account credit limit increase and decrease for each transaction beeing made.
* An Account credit limit cannot be negative.
* A Transaction is associated with a specific Account.
* A Transaction has an amount, a creation date and a type.
* A Transaction type can be "cash", "installment", "withdrawal" or "payment".
* A Transaction with a positive value must be a "payment".
* A Transaction with a negative value cannot be a "payment".

&nbsp;
&nbsp;

Database model
--------------

[![Database model](/docs/README.md/db_model.png?raw=true "Database model")](https://app.diagrams.net/#Uhttps://github.com/campisano/java_sample_web_payment_app/blob/master/docs/README.md/db_model.png?raw=true)
