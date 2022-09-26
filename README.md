# public_my_example
Demo Link [Jwt authentication api](http://springbootawsdemo-env.eba-uddtbvn6.us-east-1.elasticbeanstalk.com/).
API Authentication : Test by POSTMAN
| Methods |    URL    |        Action                         |       Require                                     |
|---------|-----------|---------------------------------------|---------------------------------------------------|
| POST    |  /auth    | authentication and get access token   | username and password                             |
| GET     |  /user    | access User's content                 | Headers KEY Authorization , VALUE : access token  |
| GET     |  /mod     | access Moderator's content            | Headers KEY Authorization , VALUE : access token  |
| GET     | /admin    | access Admin's content                | Headers KEY Authorization , VALUE : access token  |

Example : Method : POST , URL : http://hostname/auth
Body raw JSON : {"username":"user1","password":"pass1"}

**User for Test**
| Username  | Password  | Roles     |
|-----------|-----------|-----------|
| john      | 123456    |ADMIN      |
| jack      | 123456    |MODERATOR  |
| jane      | 123456    |USER       |

