package com.example.securityjwt;

import com.example.securityjwt.model.SignUpRequestBody;
import com.example.securityjwt.sevice.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SignUpResponseBodyTests {

    @Autowired
    UserService userService;

    @Test
    public void testSignUpRequestBody(){
        String strPass = "123456";
        SignUpRequestBody signUpRequestBody = new SignUpRequestBody(
                "test4@gmail.com","test4",strPass,"ROLE_USER"
        );

        System.out.println("signUpRequestBody.getPassword()"+signUpRequestBody.getPassword());
        //System.out.println("passwordEncoder "+passwordEncoder.encode(strPass));

        //assertTrue(1=1);
        //assertTrue(passwordEncoder.matches(signUpRequestBody.getPassword(),strPass));


    }


}
