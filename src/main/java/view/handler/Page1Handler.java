package view.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import domain.UserRoles;
import service.UserService;
import util.HttpServerUtils;

public class Page1Handler implements HttpHandler {
    private UserService userService;

    public Page1Handler(UserService userService) {
        this.userService = userService;
    }

    public void handle(HttpExchange t) throws IOException {
        String userName = HttpServerUtils.getUserNameOfSession(t);
        boolean validRole = userService.validateRole(userName, UserRoles.PAGE_1);
        if (validRole) {
            String response = HttpServerUtils.generatePageResponse(userName, UserRoles.PAGE_1);
            HttpServerUtils.sendStatusOk(t, response);
        } else {
            HttpServerUtils.sendForbiddenAcces(t, userName);
        }

    }

}