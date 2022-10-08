package com.example.securityjwt;

import com.example.securityjwt.security.JWTHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JWTServiceTests {

    @Autowired
    JWTHelper jwtHelper;

    @Test
    public void testGenarateJwtToken(){

        /*String username = "john";
        String accessToken = jwtService.generateJwtToken(username);
        System.out.println(accessToken);
        assertThat(accessToken.length()).isGreaterThan(0);*/

    }

}
