package view.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import util.HttpServerUtils;
import util.MustacheCompiler;

public class LoginHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        String loginForm = MustacheCompiler.generate(new Object(), "html/login.html");
        HttpServerUtils.sendStatusOk(t, loginForm);
    }
}
