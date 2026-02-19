E-Commerce Microservices Project

This project is a backend E-Commerce system built using microservices.

Instead of one big application, it is divided into small services.

Services in this project

1)Eureka Server - registers all services
2)API Gateway - single entry point for all requests
3)Product Service - manages products
4)Order Service - manages orders
Technologies Used

1.Spring Boot
2.Spring Cloud
3.Eureka Server
4.API Gateway
5.Feign Client
6.Resilience4j
7.H2 Database
8.Spring Data JPA
9.Lombok

Flow of the project

1.Client sends request
2.Request goes to API Gateway
3.Gateway forwards it to correct service using Eureka
4.Order Service uses Feign Client to talk to Product Service
5.Product stock reduces and order is saved

Main Methods

Product Service

1.Add product
2.Get all products
3.Get product by id
4.Update product
5.Delete product
6.Reduce product quantity
7.Order Service

Place order
1.Get order by id
2.Get all orders
