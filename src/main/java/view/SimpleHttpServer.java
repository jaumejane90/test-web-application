package view;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import service.SessionService;
import service.UserService;
import view.filter.ParameterFilter;
import view.handler.UserResourceHandler;

public class SimpleHttpServer {

    public static final String LOGIN_PATH = "/login";
    public static final String HOME_PATH = "/home";
    public static final String LOGOUT_PATH = "/logout";
    public static final String AUTHENTICATE_PATH = "/authenticate";
    public static final String PAGE_1_PATH = "/page_1";
    public static final String PAGE_2_PATH = "/page_2";
    public static final String PAGE_3_PATH = "/page_3";
    public static final String USER_PATH = "/user";

    private HttpServer server;
    private UserService userService;

    public SimpleHttpServer(int port, SessionService sessionService, UserService userService) throws IOException {
        this.userService = userService;

        server = HttpServer.create(new InetSocketAddress(port), 0);

        createUserResourceContext();

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


    private void createUserResourceContext() {
        HttpContext user = server.createContext(USER_PATH, new UserResourceHandler(userService));
        user.setAuthenticator(new BasicAuthenticator("") {
            @Override
            public boolean checkCredentials(String user, String pwd) {
                return userService.authenticate(user, pwd);
            }
        });
        user.getFilters().add(new ParameterFilter());
    }

}
