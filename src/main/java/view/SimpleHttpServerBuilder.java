package view;

import java.io.IOException;

import service.SessionService;
import service.UserService;
import view.handler.AuthenticationHandler;
import view.handler.HomeHandler;
import view.handler.LoginHandler;
import view.handler.LogoutHandler;
import view.handler.Page1Handler;
import view.handler.Page2Handler;
import view.handler.Page3Handler;
import view.handler.UserResourceHandler;

public class SimpleHttpServerBuilder {
    private int port;
    private UserService userService;

    private SessionService sessionService;

    private SimpleHttpServerBuilder() {
    }

    public static SimpleHttpServerBuilder aSimpleHttpServer() {
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

    public SimpleHttpServer build() throws IOException {
        SimpleHttpServer simpleHttpServer = new SimpleHttpServer(port, sessionService, userService);
        return simpleHttpServer;
    }
}
