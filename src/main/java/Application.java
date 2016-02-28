import java.io.IOException;

import repository.UserRepository;
import repository.impl.UserRepositoryInMemory;
import service.SessionService;
import service.UserService;
import service.impl.GuavaCacheSessionService;
import service.impl.UserServiceImpl;
import util.MainUtils;
import view.SimpleHttpServer;
import view.SimpleHttpServerBuilder;
import view.filter.SessionFilter;
import view.handler.HomeHandler;
import view.handler.LoginHandler;
import view.handler.LogoutHandler;
import view.handler.Page1Handler;
import view.handler.Page2Handler;
import view.handler.Page3Handler;

public class Application {

    public static void main(String[] args) throws IOException {
        int serverPort = MainUtils.getDefaultPortOrArgsPort(args);

        UserRepository userRepository = new UserRepositoryInMemory();
        SessionService sessionService = new GuavaCacheSessionService();
        UserService userService = new UserServiceImpl(userRepository);

        SimpleHttpServer simpleHttpServer = SimpleHttpServerBuilder
                .aSimpleHttpServer()
                .withPort(serverPort)
                .withSessionService(sessionService)
                .withUserService(userService)
                .withHandler(SimpleHttpServer.LOGIN_PATH, new LoginHandler())
                .withHandlerAndFilter(SimpleHttpServer.HOME_PATH, new HomeHandler(), new SessionFilter(sessionService))
                .withHandlerAndFilter(SimpleHttpServer.LOGOUT_PATH, new LogoutHandler(sessionService), new SessionFilter(sessionService))
                .withHandlerAndFilter(SimpleHttpServer.PAGE_1_PATH, new Page1Handler(userService), new SessionFilter(sessionService))
                .withHandlerAndFilter(SimpleHttpServer.PAGE_2_PATH, new Page2Handler(userService), new SessionFilter(sessionService))
                .withHandlerAndFilter(SimpleHttpServer.PAGE_3_PATH, new Page3Handler(userService), new SessionFilter(sessionService))
                .build();

        simpleHttpServer.start();

        System.out.println("Server listening on port " + serverPort);
    }

}
