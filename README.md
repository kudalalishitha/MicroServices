E-Commerce Microservices Project

This project is a backend E-Commerce system built using microservices.

Instead of one big application, it is divided into small services.

Services in this project

Eureka Server - registers all services
API Gateway - single entry point for all requests
Product Service - manages products
Order Service - manages orders
Technologies Used

Spring Boot
Spring Cloud
Eureka Server
API Gateway
Feign Client
Resilience4j
H2 Database
Spring Data JPA
Lombok
Flow of the project

Client sends request
Request goes to API Gateway
Gateway forwards it to correct service using Eureka
Order Service uses Feign Client to talk to Product Service
Product stock reduces and order is saved
Main Methods

Product Service

Add product
Get all products
Get product by id
Update product
Delete product
Reduce product quantity
Order Service

Place order
Get order by id
Get all orders
