package com.jaume.view.handler;

import java.io.IOException;

import com.jaume.util.HttpServerUtils;
import com.jaume.util.MustacheCompiler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class LoginHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        String loginForm = MustacheCompiler.generate(new Object(), "html/login.html");
        HttpServerUtils.sendStatusOk(t, loginForm);
    }
}
