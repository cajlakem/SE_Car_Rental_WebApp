# SE Car Rental WebApp

We have separated the logic of the backend and created a separate DB for each service (as it can be seen on the architectural overview) and we have used a Rabbit MQ messaging cluster:
- Sign-up Order Microservice:
  Spring-boot application + MongoDB
- Authorization Microservice:
  Spring-boot application + redis DB
- Customer Data Microservice:
  Spring-boot application + MySQL DB
- Inventory Microservice:
  Spring-boot application + PostgreSQL DB
- Sales Order Management Microservice:
  Spring-boot application + MongoDB

Each part of the application is containerized with docker, the whole system can be started locally with the “docker-compose up” command, if docker is installed. The application is also deployed to AWS and it is publicly available under the following IP-address: http://52.59.208.44/

# How to deploy locally

<code>cd docker-compose-microservices</code>
<code>
</code>
<code>docker-compose up --build</code>


