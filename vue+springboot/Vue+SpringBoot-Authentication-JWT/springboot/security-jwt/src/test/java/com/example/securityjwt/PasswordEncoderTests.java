package com.example.securityjwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PasswordEncoderTests {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void testPasswordEncoder() {
        String rawPass = "myPassword";
        String strPasswordEncode = passwordEncoder.encode(rawPass);
        System.out.println(strPasswordEncode);
        assertTrue(passwordEncoder.matches(rawPass, strPasswordEncode));

    }

}
