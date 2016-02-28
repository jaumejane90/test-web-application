package view.handler;

import java.io.IOException;
import java.util.UUID;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpPrincipal;

import service.SessionService;
import util.HttpServerUtils;

public class AuthenticationHandler implements HttpHandler {
    private SessionService sessionService;

    public AuthenticationHandler(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public void handle(HttpExchange t) throws IOException {
        HttpPrincipal httpPrincipal = t.getPrincipal();
        String userName = httpPrincipal.getName();

        String sessionToken = UUID.randomUUID().toString();
        sessionService.newSession(sessionToken, userName);
        String response = "{\"sessionToken\": \"" + sessionToken + "\"}";
        HttpServerUtils.sendStatusOk(t, response);
    }
}
