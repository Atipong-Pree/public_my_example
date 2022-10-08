package com.example.securityjwt.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("")
public class TestController {


    @GetMapping("/")
    public String hello(){
        String txt = "API Authentication !! aaaa : ";
        txt += "Test by POSTMAN <br>";
        txt += "Methods : POST , URL : /auth  , Action : Authentication for get access token ";
        txt += ", Require : username and password"+"<br>";
        txt += "Methods : GET  , URL : /user  , Action : access User's content ";
        txt += ", Require : Headers KEY  Authorization , VALUE : access token"+"<br>";
        txt += "Methods : GET  , URL : /mod   , Action : access Moderator's content ";
        txt += ", Require : Headers KEY  Authorization , VALUE : access token"+"<br>";
        txt += "Methods : GET  , URL : /admin , Action : access Admin's content ";
        txt += ", Require : Headers KEY  Authorization , VALUE : access token"+"<br>";
        txt += "<br>";
        txt += "Example : Method : POST , URL : http://hostname/auth <br>";
        txt += "Body raw JSON : {\"username\":\"user1\",\"password\":\"pass1\"}"+"<br>";
        txt += "<br>";
        txt += "User for Test <br>";
        txt += "Username : john , Password : 123456 , ROLE : ADMIN <br>";
        txt += "Username : jack , Password : 123456 , ROLE : MODERATOR <br>";
        txt += "Username : jane , Password : 123456 , ROLE : USER";

        return txt;
    }

    @GetMapping("/admin")
    public String admin(){
        return "Hello Admin !!";
    }

    @GetMapping("/mod")
    public String moderator(){
        return "Hello Moderator !!";
    }
    @GetMapping("/user")
    public String user(){
        return "Hello User !!";
    }
}
