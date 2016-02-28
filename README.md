# test-web-application
web application using com.sun.net.httpserver

# Run Test
```sh
gradle clean test
```


# Run app
```sh
gradle clean build 
java -jar /build/libs/test-web-application.jar
```

# Web pages
* http://localhost:8080/login - Login page

* http://localhost:8080/home - Home

* http://localhost:8080/page_1 - User need to have role PAGE_1 or ADMIN to can acces
* http://localhost:8080/page_2 - User need to have role PAGE_2 or ADMIN to can acces
* http://localhost:8080/page_3 - User need to have role PAGE_3 or ADMIN to can acces

# Default Users
All the users have password 123

* UserRole1 -> can acces page_1
* UserRole2 -> can acces page_2
* UserRole3 -> can acces page_3
* UserRole1And3 -> can acces page_1 and page_3
* UserRoleAdmin -> can acces all pages

# User Resource (API)
* API -> Basic Auth
* UserRole* can only GET from API, except UserRoleAdmin 

# Create Users (API) (Only Admin Role)
```sh
curl -i -H "Content-Type: application/json" -X POST -d '{"userName":"xyz","password":"xyz","roles":["PAGE_1","PAGE_2"]}' http://localhost:8080/user --user UserRoleAdmin:123
```

# Update Users (API) (Only Admin Role)
```sh
(Update all camps, null camp is removed)
curl -i -H "Content-Type: application/json" -X PUT -d '{"userName":"xyz","password":"xyz","roles":["PAGE_1"]}' http://localhost:8080/user --user UserRoleAdmin:123
```

# Delete Users (API) (Only Admin Role)
```sh
curl -i -H "Content-Type: application/json" -X DELETE -d '{"userName":"xyz"}' http://localhost:8080/user --user UserRoleAdmin:123
```

# Get Users (API)
```sh
curl -i -H "Content-Type: application/json" -X GET http://localhost:8080/user?userName=UserRole1And3 --user UserRole1:123

displaying -> {"userName":"UserRole1And3","roles":["PAGE_1","PAGE_3"]}
```










