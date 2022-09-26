package com.example.securityjwt.security;


import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JWTService {
    static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;//24hr //  1000*30 30 seconds timeout
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";
    static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    static UserDetails userDetails = null;



    static String generateJwtToken(String username, Authentication auth) {
        try {
            userDetails = (UserDetails) auth.getPrincipal();
            String JWT = Jwts.builder()
                    .setSubject(username)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SECRET_KEY)
                    .compact();
            //res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);

            return TOKEN_PREFIX + " " + JWT;
        }catch (JwtException jex){
             throw new JwtException("Generate Token Fail");
        }

    }


    static String getUserNameFromJwtToken(String token){

        try {
            String username = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            return username;
        }catch (JwtException ex){
            throw new JwtException("Token invalid");
        }

    }

    static Authentication getAuthenticationFromToken(String token){

        String username = getUserNameFromJwtToken(token);

        if(username != null && userDetails!= null) {

            Authentication authentication =  new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());

            return authentication;

        }else {
            return null;
        }

    }


}
