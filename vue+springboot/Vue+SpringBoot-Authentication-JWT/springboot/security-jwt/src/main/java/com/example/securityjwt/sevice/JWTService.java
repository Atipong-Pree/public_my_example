package com.example.securityjwt.sevice;

import com.example.securityjwt.repository.UserRepository;
import com.example.securityjwt.security.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class JWTService {

    @Autowired
    JWTHelper jwtHelper;
    @Autowired
    UserRepository userRepository;

    public String getAccessToken(HttpServletRequest request){
        return request.getHeader(jwtHelper.HEADER_STRING);
    }

    public Boolean checkUserName(String accessToken) {

        String username = jwtHelper.getUserNameFromJwtToken(accessToken);
        Boolean result = userRepository.existsByUsername(username);

        return result;
    }

    public String getUserName(String accessToken) {
        return jwtHelper.getUserNameFromJwtToken(accessToken);

    }

}
