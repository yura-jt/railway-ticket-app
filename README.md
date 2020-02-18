<p align="center"><img width=45% src="https://github.com/yura-jt/railway-ticket-app/blob/dev/web/img/logo.png"></p>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
[![Build Status](https://travis-ci.com/yura-jt/railway-ticket-app.svg?branch=dev)](https://travis-ci.com/yura-jt/railway-ticket-app)
[![codecov](https://codecov.io/gh/yura-jt/railway-ticket-app/branch/dev/graph/badge.svg)](https://codecov.io/gh/yura-jt/railway-ticket-app)

## Contents

  - [Overview](#overview)
  - [Technology stack](#technology-stack)
  - [Deployment requirements](#deployment-requirements)
  - [Installation](#installation)
      - [Clone and build](#clone-and-build)
      - [Configure MySQL](#configure-mysql)
      - [Run application](#run-application)
  - [Functionality](#functionality)
  
  
## Overview
Railway ticket booking is application that provides possibility for using online service as a train tickets reservation platform.


## Technology stack
* Java 8
* Maven
* servlets
* JSP
* JSTL
* Hikari connection pool
* Apache Tomcat
* log4j2
* H2
* MySQL
* Mockito
* Junit


## Deployment requirements
* Java 1.8 or later
* Maven 3.0 or later
* Apache Tomcat 9.0 or later


## Installation

### Clone and build
First, clone the project:
```
$ git clone https://github.com/yura-jt/railway-ticket-app.git
```
Then, install it:
```
$ cd railway-ticket-app
$ mvn clean install
```

### Configure mysql
* use provided at src/resources/db/mysql.properties credentials or change it to yours 
* run scheme sql script (__src/resources/db/sql/scheme.sql__) before first run of application
* run test data sql script (__src/resources/db/sql/data.sql__) before first run of application

### Run application
* Set up project as web project
* Enable maven auto-import
* Set up project Java SDK
* Make war from module at Project structure/artifact menu
* Add Run configuration based on Apache tomcat Local server instance
* Run application with mentioned above 

## Functionality

Railway ticket booking online service provides next functionality:
* Login and registrations for users
* Making order for ticket reservation by passenger
* Invoicing bills due to orders
* After making bill payment passenger receive ticket
* Passenger can check train schedule
* Admin can manage passenger list, bills and orders and could change their status
* Admin could change ticket tariffs