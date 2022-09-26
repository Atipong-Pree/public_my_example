package com.example.securityjwt.security;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ErrorResponseBody extends AbstractResponseBoby{


    public ErrorResponseBody(Date timestamp, HttpStatus status, String message) {
        super(timestamp, status, message);
    }

    public ErrorResponseBody() {
        super();
    }
}
