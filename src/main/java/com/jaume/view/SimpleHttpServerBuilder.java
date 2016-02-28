package com.jaume.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpHandler;

public class SimpleHttpServerBuilder {
    private static HashMap<String, HttpHandler> handlers;
    private static HashMap<String, Filter> filters;
    private static HashMap<String, Boolean> authenticatedPaths;
    private Authenticator authenticator;
    private int port;

    private SimpleHttpServerBuilder() {
    }

    public static SimpleHttpServerBuilder aSimpleHttpServer() {
        handlers = new HashMap<>();
        filters = new HashMap<>();
        authenticatedPaths = new HashMap<>();
        return new SimpleHttpServerBuilder();
    }

    public SimpleHttpServerBuilder withPort(int port) {
        this.port = port;
        return this;
    }

    public SimpleHttpServerBuilder withHandler(String path, HttpHandler httpHandler, boolean auth) {
        handlers.put(path, httpHandler);
        authenticatedPaths.put(path, auth);
        return this;
    }

    public SimpleHttpServerBuilder withHandler(String path, HttpHandler httpHandler) {
        return withHandler(path, httpHandler, false);
    }

    public SimpleHttpServerBuilder withHandlerAndFilter(String path, HttpHandler httpHandler, Filter filter, boolean auth) {
        handlers.put(path, httpHandler);
        filters.put(path, filter);
        authenticatedPaths.put(path, auth);
        return this;
    }

    public SimpleHttpServerBuilder withHandlerAndFilter(String path, HttpHandler httpHandler, Filter filter) {
        return withHandlerAndFilter(path, httpHandler, filter, false);
    }

    public SimpleHttpServerBuilder withAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
        return this;
    }

    public SimpleHttpServer build() throws IOException {
        SimpleHttpServer simpleHttpServer = new SimpleHttpServer(port);

        for (Map.Entry entry : handlers.entrySet()) {
            String path = (String) entry.getKey();
            HttpHandler httpHandler = (HttpHandler) entry.getValue();
            Filter filter = filters.get(path);
            boolean authenticatedPath = authenticatedPaths.get(path);
            simpleHttpServer.createPathContext(path, httpHandler, filter, authenticator, authenticatedPath);
        }
        return simpleHttpServer;
    }
}
