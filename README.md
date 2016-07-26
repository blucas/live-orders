# Live Orders Project

Live Orders is a Spring Boot based project with a RESTful API service for CRUD operations. 

## How to build

This is a standard Gradle project, so just run `./gradlew build test` and the project will be compiled and the tests will run.

You require the following to build the project:

* [Oracle JDK 8](http://www.oracle.com/technetwork/java/)

JDK 8 is required to build and run this project.

## Running the Project

The project uses [Spring Boot](http://projects.spring.io/spring-boot/) which makes configuring and running a web based application a breeze.

In order to run the project from the command line do the following:

From the project base directory you can run `./gradlew bootRun` which will start the application on `http://localhost:8080`

## Using the Project API

The project is configured with three RESTful API operations:

* [Register Order](#register-order)
* [Cancel Order](#cancel-order)
* [Order Board](#order-board)

### Register Order

When called, this API operation will register an order in the system.

#### Operation Examples

The example below will register a BUY order in the system and return the order key which can be used to cancel the order.

```
$ curl -v -X POST -H "Content-Type: application/json" -d '{"quantity": 99,"userId": 2,"orderType": "BUY","pricePerUnit": 800}' http://localhost:8080/orders
*   Trying ::1...
* Connected to localhost (::1) port 8080 (#0)
> POST /orders HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.43.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 67
> 
* upload completely sent off: 67 out of 67 bytes
< HTTP/1.1 200 OK
< Server: Apache-Coyote/1.1
< Content-Type: text/plain;charset=UTF-8
< Content-Length: 36
< Date: Tue, 26 Jul 2016 11:06:19 GMT
< 
* Connection #0 to host localhost left intact
e2356e38-1de6-4ba4-b75e-68b5f8297aa7
```

### Cancel Order

When called, this API operation will cancel an existing order in the system using the `orderKey` supplied.

#### Operation Examples

The example below cancels an existing order in the system.

```
$ curl -v -X DELETE http://localhost:8080/orders/e2356e38-1de6-4ba4-b75e-68b5f8297aa7
*   Trying ::1...
* Connected to localhost (::1) port 8080 (#0)
> DELETE /orders/e2356e38-1de6-4ba4-b75e-68b5f8297aa7 HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.43.0
> Accept: */*
> 
< HTTP/1.1 200 OK
< Server: Apache-Coyote/1.1
< Content-Length: 0
< Date: Tue, 26 Jul 2016 11:12:04 GMT
< 
* Connection #0 to host localhost left intact
```

### Order Board

When called, this API operation will return the aggregated BUY and SELL orders. BUY orders are sorted in descending order, and SELL orders are sorted in ascending order.

#### Operation Examples

The example below shows an order board with two BUY and two SELL orders. The BUY orders are sorted by `pricePerUnit` in descending order. The SELL orders are sorted by `pricePerUnit` in ascending order.

```
$ curl -v http://localhost:8080/orders
*   Trying ::1...
* Connected to localhost (::1) port 8080 (#0)
> GET /orders HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.43.0
> Accept: */*
> 
< HTTP/1.1 200 OK
< Server: Apache-Coyote/1.1
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Tue, 26 Jul 2016 11:16:32 GMT
< 
* Connection #0 to host localhost left intact
{"buyOrderBoard":[{"pricePerUnit":800,"quantity":198},{"pricePerUnit":600,"quantity":39}],"sellOrderBoard":[{"pricePerUnit":300,"quantity":769},{"pricePerUnit":600,"quantity":59}]}
```