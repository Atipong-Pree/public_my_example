package com.example.securityjwt.security;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class AuthResponeBody extends AbstractResponseBoby {

    private String accessToken;

    public AuthResponeBody(Date timestamp, HttpStatus status, String message, String accessToken) {
        super(timestamp, status, message);
        this.accessToken = accessToken;
    }

    public AuthResponeBody() {
        super();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
