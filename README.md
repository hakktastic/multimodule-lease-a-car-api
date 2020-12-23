# Multimodule lease a Car Api

:information_source: This project is a continuation of the [singlemodule lease a car api](https://github.com/hakktastic/lease-a-car-api) project. 

### Background
I started developing this application as an assessment for a Job application. 
After implementing basic microservices functionalities in the [first project](https://github.com/hakktastic/lease-a-car-api) 
I wanted update this project into a multi module Maven project and after that continue my development process.

### The application
The application itself contains of four microservices (see drawing below). The lease-calculation-service calls the three other services in order to make a lease rate calcultion. The micro services called each have an in-memory H2 database with prefilled data to make the calculation possible. 

*Drawing will be updated with naming server & load balancer*

![drawing](https://github.com/hakktastic/lease-a-car-api/blob/main/singlemodule-lease-a-car-api/Drawing.jpg) 

##### Application Url's

Service | Url:port | H2 Console | API Documentation
------------ | ------------- | -------------  | -------------
lease-calculation-service | http://localhost:8080 | - | http://localhost:8080/swagger-ui/#/lease-calculation-controller
customer-service | http://localhost:8081 | http://localhost:8083/h2-console | http://localhost:8081/swagger-ui/#/customer-controller
car-service | http://localhost:8082 | http://localhost:8082/h2-console | http://localhost:8082/swagger-ui/#/car-controller
interest-rate-calculation-service | http://localhost:8083 | http://localhost:8083/h2-console | http://localhost:8083/swagger-ui/#/interest-rate-controller
Netflix Eureka Naming Server | http://localhost:8761 | - | -
Netflix Zuul Api Gateway Server | http://localhost:8765 | - | -
Zipkin  | http://localhost:9411/zipkin/ | - | -
RabbitMQ | http://localhost:15672 | - | -

##### Technologies
The technologies used for the initial implementation of this application were as follows:

* Java
* Spring Boot:
  * Spring Web
  * Spring Data JPA
  * Spring Boot Devtools
  * Spring Boot Actuator
  * Spring Cloud OpenFeign
  * Spring Cloud Config Server
  * Spring Cloud Sleuth
  * Netflix Ribbon Load Balancer
  * Netflix Eureka Naming Server
  * Netflix Zuul API Gateway Server
  * Netflix Hystrix
  * MQRabbit
  * Zipkin Tracing Server
  * H2 Database
  * Springfox (Swagger)
* Maven, JUnit & Mockito
* Eclipse, Postman
* Web Browser

For this project, the following additions have been made:
* Docker
* Maven Multimodule projects

### Development
The upcoming period, while completing the second course I will be working on the Spring Cloud elements like: 

#### SHORT TERM
- [x] Refactor Maven projects to Maven multi-module projects in combination with DDD
- [ ] Dockerize projects:
	- [x] include in maven build process (spotify docker maven plugin)
	- [x] improve caching by splitting up dependencies & classes (maven dependency plugin)
	- [ ] Change from H2 to Docker MySQL database; use H2 for tests
- [ ] Kubernetes:
	- [ ] GKE - Google Kubernetes Engine
	- [ ] EKS - Amazon Elastic Kubernetes Service
	- [ ] AKS - Azure Kubernetes Service
- [ ] Spring Security > how do I secure Rest calls?

#### MID TERM
- [ ] Instead of Netflix Ribbon load balancer try use Spring Cloud Load Balancer
- [ ] Spring Cloud Bus > Dynamic configuration changing

### Setup
In order to run this application within you favourite IDE:

* Checkout this project and it's module from my Github
* Open a terminal and go to the root of the multimodule project

* Run following Docker command:
	* The --build option will automatically build the images
```
docker-compose up --build
```
	* Otionally, one could run the containers in detached mode
```
docker-compose up --build -d
```	
