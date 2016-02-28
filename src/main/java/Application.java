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
                .withHandler(SimpleHttpServer.HOME_PATH, new HomeHandler())
                .withFilter(SimpleHttpServer.HOME_PATH, new SessionFilter(sessionService))
                .build();

        simpleHttpServer.start();

        System.out.println("Server listening on port " + serverPort);
    }

}
