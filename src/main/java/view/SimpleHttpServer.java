package view;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import service.SessionService;
import service.UserService;
import view.filter.ParameterFilter;
import view.filter.SessionFilter;
import view.handler.AuthenticationHandler;
import view.handler.Page1Handler;
import view.handler.Page2Handler;
import view.handler.Page3Handler;
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

        createAuthenticateContext(sessionService);

        createPrivatePagesContext(sessionService);

        createUserResourceContext();

        server.setExecutor(null);
    }

    public void start() {
        server.start();
    }

    public void createPathContext(String path, HttpHandler httpHandler) {
        server.createContext(path, httpHandler);
    }

    public void createPathContext(String path, HttpHandler httpHandler, Filter filter) {
        HttpContext context = server.createContext(path, httpHandler);
        context.getFilters().add(filter);
    }

    private void createAuthenticateContext(SessionService sessionService) {
        HttpContext auth = server.createContext(AUTHENTICATE_PATH, new AuthenticationHandler(sessionService));
        auth.setAuthenticator(new BasicAuthenticator("") {
            @Override
            public boolean checkCredentials(String user, String pwd) {
                return userService.authenticate(user, pwd);
            }

            @Override
            public Result authenticate(HttpExchange var1) {
                Result result = super.authenticate(var1);
                var1.getResponseHeaders().remove("WWW-Authenticate");
                return result;
            }
        });
    }

    private void createPrivatePagesContext(SessionService sessionService) {
        HttpContext page1 = server.createContext(PAGE_1_PATH, new Page1Handler(userService));
        page1.getFilters().add(new SessionFilter(sessionService));
        HttpContext page2 = server.createContext(PAGE_2_PATH, new Page2Handler(userService));
        page2.getFilters().add(new SessionFilter(sessionService));
        HttpContext page3 = server.createContext(PAGE_3_PATH, new Page3Handler(userService));
        page3.getFilters().add(new SessionFilter(sessionService));
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
