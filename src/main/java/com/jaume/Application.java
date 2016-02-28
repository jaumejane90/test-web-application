package com.jaume;

import java.io.IOException;

import com.jaume.constants.HttpServer;
import com.jaume.repository.UserRepository;
import com.jaume.repository.impl.UserRepositoryInMemory;
import com.jaume.security.CustomBasicAuthenticator;
import com.jaume.service.SessionService;
import com.jaume.service.UserService;
import com.jaume.service.impl.GuavaCacheSessionService;
import com.jaume.service.impl.UserServiceImpl;
import com.jaume.util.MainUtils;
import com.jaume.view.SimpleHttpServer;
import com.jaume.view.SimpleHttpServerBuilder;
import com.jaume.view.filter.ParameterFilter;
import com.jaume.view.filter.SessionFilter;
import com.jaume.view.handler.AuthenticationHandler;
import com.jaume.view.handler.HomeHandler;
import com.jaume.view.handler.LoginHandler;
import com.jaume.view.handler.LogoutHandler;
import com.jaume.view.handler.Page1Handler;
import com.jaume.view.handler.Page2Handler;
import com.jaume.view.handler.Page3Handler;
import com.jaume.view.handler.UserResourceHandler;

public class Application {

    public static void main(String[] args) throws IOException {
        int serverPort = MainUtils.getDefaultPortOrArgsPort(args);

        UserRepository userRepository = new UserRepositoryInMemory();
        SessionService sessionService = new GuavaCacheSessionService();
        UserService userService = new UserServiceImpl(userRepository);

        SimpleHttpServer simpleHttpServer = SimpleHttpServerBuilder
                .aSimpleHttpServer()
                .withPort(serverPort)
                .withAuthenticator(new CustomBasicAuthenticator(HttpServer.Authenticator.DEFAULT_MESSAGE, userService))
                .withHandler(HttpServer.Path.LOGIN_PATH, new LoginHandler())
                .withHandlerAndFilter(HttpServer.Path.HOME_PATH, new HomeHandler(), new SessionFilter(sessionService))
                .withHandlerAndFilter(HttpServer.Path.LOGOUT_PATH, new LogoutHandler(sessionService), new SessionFilter(sessionService))
                .withHandlerAndFilter(HttpServer.Path.PAGE_1_PATH, new Page1Handler(userService), new SessionFilter(sessionService))
                .withHandlerAndFilter(HttpServer.Path.PAGE_2_PATH, new Page2Handler(userService), new SessionFilter(sessionService))
                .withHandlerAndFilter(HttpServer.Path.PAGE_3_PATH, new Page3Handler(userService), new SessionFilter(sessionService))
                .withHandler(HttpServer.Path.AUTHENTICATE_PATH, new AuthenticationHandler(sessionService), true)
                .withHandlerAndFilter(HttpServer.Path.USER_PATH, new UserResourceHandler(userService), new ParameterFilter(), true)
                .build();

        simpleHttpServer.start();

        System.out.println("Server listening on port " + serverPort);
    }

}
