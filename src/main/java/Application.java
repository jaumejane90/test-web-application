import java.io.IOException;

import repository.UserRepository;
import repository.impl.UserRepositoryInMemory;
import service.SessionService;
import service.UserService;
import service.impl.GuavaCacheSessionService;
import service.impl.UserServiceImpl;
import view.SimpleHttpServer;

public class Application {

    public static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
    }


}
