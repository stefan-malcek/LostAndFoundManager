# Lost&Found Manager

The main goal of this project is to develop an application that would help people to find lost items or report things that have been found. For more information on how this application should look, look at the project wiki.




REST API curl examples:

GET ALL

curl -X GET http://localhost:8080/pa165/rest/categories 

GET ONE

curl -X GET http://localhost:8080/pa165/rest/categories/1

DELETE

curl -X DELETE http://localhost:8080/pa165/rest/categories/1

UPDATE

curl -H "Content-Type: application/json" -X PUT -d "{"description":"eeee","name":"test}" http://localhost:8080/pa165/rest/categories/1

POST

curl -H "Content-Type: application/json" -X POST -d "{"description":"xyz","name":"asdasd}" http://localhost:8080/pa165/rest/categories/create
