package com.jaume.domain.builder;

import java.util.ArrayList;

import com.jaume.domain.User;

public class UserBuilder {
    private String userName;
    private String password;
    private ArrayList<String> roles = new ArrayList<>();

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder addRole(String role) {
        this.roles.add(role);
        return this;
    }

    public User build() {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setRoles(roles);
        return user;
    }
}
