package com.jaume.security;


import com.jaume.service.UserService;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpExchange;

public class CustomBasicAuthenticator extends BasicAuthenticator {

    private UserService userService;

    public CustomBasicAuthenticator(String s, UserService userService) {
        super(s);
        this.userService = userService;
    }

    @Override
    public boolean checkCredentials(String user, String pwd) {
        return userService.authenticate(user, pwd);
    }

    @Override
    public Result authenticate(HttpExchange var1) {
        Result result = super.authenticate(var1);
        var1.getResponseHeaders().remove("WWW-Authenticate");
        return result;
    }
}
