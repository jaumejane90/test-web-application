package com.jaume.view;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SimpleHttpServer {

    public static final int SOCKET_BACKLOG = 0;

    private HttpServer server;

    public SimpleHttpServer(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), SOCKET_BACKLOG);

        server.setExecutor(null);
    }

    public void start() {
        server.start();
    }

    public void createPathContext(String path, HttpHandler httpHandler, Filter filter, Authenticator authenticator, boolean authenticatedPath) {
        HttpContext context = server.createContext(path, httpHandler);

        if (filter != null) {
            context.getFilters().add(filter);
        }
        if (authenticatedPath && authenticator != null) {
            context.setAuthenticator(authenticator);
        }
    }

}
