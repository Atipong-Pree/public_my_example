# public_my_example
Demo Link hostname : [Jwt authentication api](http://springbootawsdemo-env.eba-uddtbvn6.us-east-1.elasticbeanstalk.com/).
API Authentication : Test by POSTMAN
| Methods |    URL    |        Action                         |       Require                                     |
|---------|-----------|---------------------------------------|---------------------------------------------------|
| POST    |  [/auth](http://springbootawsdemo-env.eba-uddtbvn6.us-east-1.elasticbeanstalk.com/auth)    | authentication and get access token   | username and password                             |
| GET     |  [/user](http://springbootawsdemo-env.eba-uddtbvn6.us-east-1.elasticbeanstalk.com/user)    | access User's content                 | Headers KEY Authorization , VALUE : access token  |
| GET     |  [/mod](http://springbootawsdemo-env.eba-uddtbvn6.us-east-1.elasticbeanstalk.com/mod)     | access Moderator's content            | Headers KEY Authorization , VALUE : access token  |
| GET     | [/admin](http://springbootawsdemo-env.eba-uddtbvn6.us-east-1.elasticbeanstalk.com/admin)    | access Admin's content                | Headers KEY Authorization , VALUE : access token  |

Example : Method : POST , URL : [http://hostname/auth](http://springbootawsdemo-env.eba-uddtbvn6.us-east-1.elasticbeanstalk.com/auth)
Body raw JSON : {"username":"user1","password":"pass1"}

**User for Test**
| Username  | Password  | Roles     |
|-----------|-----------|-----------|
| john      | 123456    |ADMIN      |
| jack      | 123456    |MODERATOR  |
| jane      | 123456    |USER       |

