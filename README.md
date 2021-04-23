E-Stock Market

This is a Micro services project created with Spring boot and Java version 1.8.
This project contains three microservices:

* EurekaMicroService: This service works as a eureka server and handles service discovery.
* CompanyMicroService: This service handles the operation for Company entity
* StockPriceMicroService: This service handles the operation for StockPrice entity

All the services are built and deployed using docker containers

To start the microservices:

* Clone this repository.
* Docker build each service using 
  * ```cd CompanyMicroService```
  * ```docker build -t company-service:v1```
  * Similarly, for other services.
* Now return to the parent directory and run ```docker-compose up```

  OR

* Run ```docker-compose up``` from the parent directory, this will first build
  and then deploy the services.
  
API Documentation:

* Company API is documented in [company_api.yaml](https://github.com/akhilgandhi/EStockMarketCaseStudy/blob/main/company_api.yaml)
* StockPrice API is documented in [stockprice_api.yaml](https://github.com/akhilgandhi/EStockMarketCaseStudy/blob/main/stockprice_api.yaml)