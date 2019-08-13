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

GET http://localhost:8080/customer-service/api/v1/product/