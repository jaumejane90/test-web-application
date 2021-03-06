package com.jaume.view.handler;

import java.io.IOException;

import com.jaume.util.HttpServerUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HomeHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        String userName = HttpServerUtils.getUserNameOfSession(t);
        String response = "Home: " + userName + " !";
        HttpServerUtils.sendStatusOk(t, response);
    }
}
