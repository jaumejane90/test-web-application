package view.filter;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpStatus;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import constants.HttpServer;
import service.SessionService;

public class SessionFilter extends Filter {

    public static final String COOKIE_HEADER = "Cookie";
    public static final String EQUAL = "=";
    public static final String SESSION_KEY = "session";
    public static final String LOCATION_HEADER = "Location";
    public static final String WEB_SERVER_LOGIN_URL = "http://localhost:8080/login";
    public static final String INC_PARAM = "?inc=";
    private SessionService sessionService;

    public SessionFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
        List<String> cookies = httpExchange.getRequestHeaders().get(COOKIE_HEADER);

        String sessionToken = getSessionTokenFromCookies(cookies);
        String userNameOfSessionToken = sessionService.getUserNameOfSession(sessionToken);

        if (userNameOfSessionToken != null) {
            httpExchange.setAttribute(HttpServer.Session.USERNAME_LOGED_ATTRIBUTE, userNameOfSessionToken);
            httpExchange.setAttribute(HttpServer.Session.SESSION_TOKEN_ATTRIBUTE, sessionToken);
            chain.doFilter(httpExchange);
        } else {
            httpExchange.getResponseHeaders().add(LOCATION_HEADER, WEB_SERVER_LOGIN_URL + INC_PARAM + httpExchange.getRequestURI());
            httpExchange.sendResponseHeaders(HttpStatus.SC_MOVED_TEMPORARILY, -1L);
        }
    }

    String getSessionTokenFromCookies(List<String> cookies) {
        String sessionToken = null;
        if (cookies != null) {
            for (String cookie : cookies) {
                sessionToken = getSessionTokenFromCookie(cookie);
                if (sessionToken != null) break;
            }
        }
        return sessionToken;
    }

    String getSessionTokenFromCookie(String cookie) {
        String sessionToken = null;
        if (cookie != null) {
            int index = cookie.indexOf(EQUAL);
            if (index > 0) {
                String key = cookie.substring(0, index);
                if (key.equals(SESSION_KEY)) {
                    sessionToken = cookie.substring(index + 1);
                }
            }
        }
        return sessionToken;
    }

    @Override
    public String description() {
        return null;
    }


}
