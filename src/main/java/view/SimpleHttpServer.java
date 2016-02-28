package view;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import service.SessionService;
import service.UserService;
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

public class SimpleHttpServer {

    private HttpServer server;

    private UserService userService;

    public SimpleHttpServer(int port, SessionService sessionService, UserService userService) {
        this.userService = userService;

        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        createLoginContext();

        createHomeContext(sessionService);

        createAuthenticateContext(sessionService);

        creatLogoutContext(sessionService);

        createPrivatePagesContext(sessionService);

        createUserResourceContext();

        server.setExecutor(null);
    }

    private void createLoginContext() {
        server.createContext("/login", new LoginHandler());
    }

    private void createHomeContext(SessionService sessionService) {
        HttpContext home = server.createContext("/home", new HomeHandler());
        home.getFilters().add(new SessionFilter(sessionService));
    }

    private void creatLogoutContext(SessionService sessionService) {
        HttpContext logout = server.createContext("/logout", new LogoutHandler(sessionService));
        logout.getFilters().add(new SessionFilter(sessionService));
    }

    private void createAuthenticateContext(SessionService sessionService) {
        HttpContext auth = server.createContext("/authenticate", new AuthenticationHandler(sessionService));
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
        HttpContext page1 = server.createContext("/page_1", new Page1Handler(userService));
        page1.getFilters().add(new SessionFilter(sessionService));
        HttpContext page2 = server.createContext("/page_2", new Page2Handler(userService));
        page2.getFilters().add(new SessionFilter(sessionService));
        HttpContext page3 = server.createContext("/page_3", new Page3Handler(userService));
        page3.getFilters().add(new SessionFilter(sessionService));
    }

    private void createUserResourceContext() {
        HttpContext user = server.createContext("/user", new UserResourceHandler(userService));
        user.setAuthenticator(new BasicAuthenticator("") {
            @Override
            public boolean checkCredentials(String user, String pwd) {
                return userService.authenticate(user, pwd);
            }
        });
        user.getFilters().add(new ParameterFilter());
    }

    public void start() {
        server.start();
    }
}
