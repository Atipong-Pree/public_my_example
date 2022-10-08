package com.example.securityjwt.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class SignUpRequestBody {
    @NotNull(message = "email can not be null")
    @NotBlank(message = "email can not be blank")
    @Email
    private String email;

    @NotBlank(message = "username can not be blank")
    @NotNull(message = "username can not be null")
    private String username;

    @NotBlank(message = "password can not be blank")
    @NotNull(message = "password can not be null")
    private String password;

    private String role;


    public SignUpRequestBody() {
    }

    public SignUpRequestBody(String email, String username, String password , String role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
