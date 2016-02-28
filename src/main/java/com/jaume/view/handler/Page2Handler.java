package com.jaume.view.handler;

import java.io.IOException;

import com.jaume.domain.UserRoles;
import com.jaume.service.UserService;
import com.jaume.util.HttpServerUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Page2Handler implements HttpHandler {
    private UserService userService;

    public Page2Handler(UserService userService) {
        this.userService = userService;
    }

    public void handle(HttpExchange t) throws IOException {
        String userName = HttpServerUtils.getUserNameOfSession(t);
        boolean validRole = userService.validateRole(userName, UserRoles.PAGE_2);
        if (validRole) {
            String response = HttpServerUtils.generatePageResponse(userName, UserRoles.PAGE_2);
            HttpServerUtils.sendStatusOk(t, response);
        } else {
            HttpServerUtils.sendForbiddenAcces(t, userName);
        }
    }
}
