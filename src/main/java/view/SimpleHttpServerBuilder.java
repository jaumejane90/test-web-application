package view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpHandler;

import service.SessionService;
import service.UserService;

public class SimpleHttpServerBuilder {
    private static HashMap<String, HttpHandler> handlers;
    private static HashMap<String, Filter> filters;
    private int port;
    private UserService userService;
    private SessionService sessionService;

    private SimpleHttpServerBuilder() {
    }

    public static SimpleHttpServerBuilder aSimpleHttpServer() {
        handlers = new HashMap<>();
        filters = new HashMap<>();
        return new SimpleHttpServerBuilder();
    }

    public SimpleHttpServerBuilder withPort(int port){
        this.port = port;
        return this;
    }

    public SimpleHttpServerBuilder withUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

    public SimpleHttpServerBuilder withSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
        return this;
    }

    public SimpleHttpServerBuilder withHandler(String path, HttpHandler httpHandler) {
        handlers.put(path, httpHandler);
        return this;
    }

    public SimpleHttpServerBuilder withHandlerAndFilter(String path, HttpHandler httpHandler, Filter filter) {
        handlers.put(path, httpHandler);
        filters.put(path, filter);
        return this;
    }

    public SimpleHttpServer build() throws IOException {
        SimpleHttpServer simpleHttpServer = new SimpleHttpServer(port, sessionService, userService);

        for (Map.Entry entry : handlers.entrySet()) {
            String path = (String) entry.getKey();
            HttpHandler httpHandler = (HttpHandler) entry.getValue();
            Filter filter = filters.get(path);
            if (filter == null) {
                simpleHttpServer.createPathContext(path, httpHandler);
            } else {
                simpleHttpServer.createPathContext(path, httpHandler, filter);
            }
        }


        return simpleHttpServer;
    }
}
