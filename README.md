# auth-service 

return 15 minutes valid token, if user/pwd is equal

available via POST at http://localhost:8080/auth-service/api/v1/auth

body :

{
    "username" : "astar",
    "password" : "seran"
}

tests are done for resteasy, packaging = war, tested on payara-5.192

# customer-service

Customer endpoints:
GET http://localhost:8080/customer-service/api/v1/customer
GET http://localhost:8080/customer-service/api/v1/customer/?name=XXX
GET http://localhost:8080/customer-service/api/v1/customer/{id}
POST http://localhost:8080/customer-service/api/v1/customer
PUT http://localhost:8080/customer-service/api/v1/customer
DELETE http://localhost:8080/customer-service/api/v1/customer/{id}

Product endpoints:
GET http://localhost:8080/customer-service/api/v1/product/
GET http://localhost:8080/customer-service/api/v1/product/?name=XXX
GET http://localhost:8080/customer-service/api/v1/product/{id}
POST http://localhost:8080/customer-service/api/v1/product/
PUT http://localhost:8080/customer-service/api/v1/product/
DELETE http://localhost:8080/customer-service/api/v1/product/{id}

Order endpoints:
GET http://localhost:8080/customer-service/api/v1/customer/{customerId}/order
GET http://localhost:8080/customer-service/api/v1/customer/{customerId}/order/?minPrice=XXX
GET http://localhost:8080/customer-service/api/v1/customer/{customerId}/order/{id}
POST http://localhost:8080/customer-service/api/v1/customer/{customerId}/order
PUT http://localhost:8080/customer-service/api/v1/customer/{customerId}/order
DELETE http://localhost:8080/customer-service/api/v1/customer/{customerId}/order/{id}