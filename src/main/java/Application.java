import java.io.IOException;

import constants.HttpServer;
import repository.UserRepository;
import repository.impl.UserRepositoryInMemory;
import security.CustomBasicAuthenticator;
import service.SessionService;
import service.UserService;
import service.impl.GuavaCacheSessionService;
import service.impl.UserServiceImpl;
import util.MainUtils;
import view.SimpleHttpServer;
import view.SimpleHttpServerBuilder;
import view.filter.ParameterFilter;
import view.filter.SessionFilter;
import view.handler.AuthenticationHandler;
import view.handler.HomeHandler;
import view.handler.LoginHandler;
import view.handler.LogoutHandler;
import view.handler.Page1Handler;
import view.handler.Page2Handler;
import view.handler.Page3Handler;
import view.handler.UserResourceHandler;

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
