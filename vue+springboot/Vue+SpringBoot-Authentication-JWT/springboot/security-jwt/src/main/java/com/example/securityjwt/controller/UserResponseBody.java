package com.example.securityjwt.controller;

import com.example.securityjwt.entity.Role;
import com.example.securityjwt.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserResponseBody {

    private Long id;
    private String username;
    private String email;
    private List<String> roles = new ArrayList<>();
    private String accessToken;

    public UserResponseBody(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        for (Role roleItem:user.getRoles()) {
            this.roles.add(roleItem.getName().toString());
        }

    }

    public String getAccessToken() {
        return accessToken;
    }


    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRoles() {
        return roles;
    }
}
