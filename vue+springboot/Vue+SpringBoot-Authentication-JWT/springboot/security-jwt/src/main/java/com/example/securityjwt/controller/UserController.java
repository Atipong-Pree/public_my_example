package com.example.securityjwt.controller;

import com.example.securityjwt.entity.User;
import com.example.securityjwt.exception.APIException;
import com.example.securityjwt.model.SignUpRequestBody;
import com.example.securityjwt.sevice.JWTService;
import com.example.securityjwt.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    JWTService jwtService;

    @GetMapping("/test")
    public String TestController(){
        return "Test !!";
    }


    @ResponseBody @GetMapping("/user")
    public UserResponseBody getUserDetails(HttpServletRequest request){
        // get token
        String jwtToken = jwtService.getAccessToken(request);
        // get username from jwt token
        String username = jwtService.getUserName(jwtToken);

        User user = userService.getUserByUserName(username);
        UserResponseBody userResponseBody = new UserResponseBody(user);
        userResponseBody.setAccessToken(jwtToken);

        return userResponseBody;
    }

    @GetMapping("/admin")
    public String admin(){
        return "Hello Admin !!";
    }


    @GetMapping("/mod")
    public String moderator(){
        return "Hello Moderator !!";
    }

    @PostMapping("/signup")
    @ResponseBody
    public UserResponseBody signup(@Valid @RequestBody SignUpRequestBody signUpRequestBody){

        // check email existing
        String email =signUpRequestBody.getEmail();
        boolean emailExist = userService.checkEmailExists(email);
        if(emailExist){
            throw new APIException("Email : "+email+" is exist");
        }

        // check user existing
        String username =signUpRequestBody.getUsername();
        boolean usernameExist = userService.checkUsernameExists(username);
        if(usernameExist){
            throw new APIException("Username : "+username+" is exist");
        }

        User user = new User();
        user.setEmail(signUpRequestBody.getEmail());
        user.setUsername(signUpRequestBody.getUsername());
        user.setPassword(userService.createdPasswordEncode(signUpRequestBody.getPassword()));
        user.setRoles(userService.getRolesUser(signUpRequestBody.getRole()));

        User newUser = userService.addUser(user);
        UserResponseBody userResponseBody = new UserResponseBody(newUser);
        return userResponseBody;
    }

    private void getRoleOfUser(String roleName){

    }



}
