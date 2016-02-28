package view.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import service.SessionService;
import util.HttpServerUtils;

public class LogoutHandler implements HttpHandler {
    private SessionService sessionService;

    public LogoutHandler(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public void handle(HttpExchange t) throws IOException {
        String sessionToken = HttpServerUtils.getSessionToken(t);

        sessionService.invalidate(sessionToken);
        String response = "{}";
        HttpServerUtils.sendStatusOk(t, response);
    }
}
