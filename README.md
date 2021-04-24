E-Stock Market

This is a Micro services project created with Spring boot and Java version 1.8.
This project contains these microservices:

* EurekaMicroService: This service works as a eureka server and handles service discovery.
* CompanyMicroService: This service handles the operation for Company entity
* StockPriceMicroService: This service handles the operation for StockPrice entity
* Grafana Dashboards
* Prometheus 

All the services are built and deployed using docker containers

To start the microservices:

* Clone this repository.
* Run ```docker-compose up --build``` from the parent directory, this will first build
  and then deploy the services.
* Or, if just want to run the services use ```docker-compose up``` from the parent directory  

If everything goes well, you can access the following services at given location:

* Eureka-Service: http://localhost:8761
* Company and Stock Price services: random port, check Eureka Dashboard
* Grafana Dashboards - http://localhost:3000
* Prometheus - http://localhost:9090
  
API Documentation:

* Company API is documented in [company_api.yaml](https://github.com/akhilgandhi/EStockMarketCaseStudy/blob/main/company_api.yaml)
  - Company API Swagger UI: http://localhost:8089/swagger-ui/
* StockPrice API is documented in [stockprice_api.yaml](https://github.com/akhilgandhi/EStockMarketCaseStudy/blob/main/stockprice_api.yaml)
  - StockPrice API Swagger UI: http://localhost:8090/swagger-ui/