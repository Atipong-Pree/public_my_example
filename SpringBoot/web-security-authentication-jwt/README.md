# Spring Boot Web Security - JWT Authentication
Demo Link hostname : [Jwt authentication api](http://springbootawsdemo-env.eba-uddtbvn6.us-east-1.elasticbeanstalk.com/)

API Authentication : Test by POSTMAN  [Donwload POSTMAN](https://www.postman.com/downloads/)
| Methods |    URL    |        Action                         |       Require                                     |
|---------|-----------|---------------------------------------|---------------------------------------------------|
| POST    |  [/auth](http://springbootawsdemo-env.eba-uddtbvn6.us-east-1.elasticbeanstalk.com/auth)    | authentication and get access token   | username and password                             |
| GET     |  [/user](http://springbootawsdemo-env.eba-uddtbvn6.us-east-1.elasticbeanstalk.com/user)    | access User's content                 | Headers KEY Authorization , VALUE : access token  |
| GET     |  [/mod](http://springbootawsdemo-env.eba-uddtbvn6.us-east-1.elasticbeanstalk.com/mod)     | access Moderator's content            | Headers KEY Authorization , VALUE : access token  |
| GET     | [/admin](http://springbootawsdemo-env.eba-uddtbvn6.us-east-1.elasticbeanstalk.com/admin)    | access Admin's content                | Headers KEY Authorization , VALUE : access token  |

Example : Method : POST , URL : [http://hostname/auth](http://springbootawsdemo-env.eba-uddtbvn6.us-east-1.elasticbeanstalk.com/auth)
Body raw JSON : {"username":"user1","password":"pass1"}

**User for tesingt**
| Username  | Password  | Roles     |
|-----------|-----------|-----------|
| john      | 123456    |ADMIN      |
| jack      | 123456    |MODERATOR  |
| jane      | 123456    |USER       |


**Test by POSTMAN**  

| TEST /auth  |
|-----------|
|<img width="726" alt="img1" src="https://user-images.githubusercontent.com/110652053/192255147-6e23e73a-1d0e-4b8a-8f00-1ca4774210d0.png">|


| TEST /user|
|-----------|
|<img width="726" alt="img2" src="https://user-images.githubusercontent.com/110652053/192255300-095b12cd-74a1-40e1-8df5-f52c3699f90b.png">|

