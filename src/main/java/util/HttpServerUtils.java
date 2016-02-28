package util;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.HttpStatus;

import com.sun.net.httpserver.HttpExchange;

import domain.UserRoles;

public class HttpServerUtils {

    public static final String HTML_FORBIDDEN_PAGE_PATH = "html/forbiddenPage.html";

    public static String getUserNameOfSession(HttpExchange t) {
        Object userName = t.getAttribute(constants.HttpServer.Session.USERNAME_LOGED_ATTRIBUTE);
        return userName != null ? userName.toString() : null;
    }

    public static String getSessionToken(HttpExchange t) {
        Object sessionToken = t.getAttribute(constants.HttpServer.Session.SESSION_TOKEN_ATTRIBUTE);
        return sessionToken != null ? sessionToken.toString() : null;
    }

    public static void sendForbiddenAcces(HttpExchange t, String userName) throws IOException {
        MustachePageDTO dto = new MustachePageDTO();
        dto.setUserName(userName);
        String response = MustacheCompiler.generate(dto, HTML_FORBIDDEN_PAGE_PATH);
        t.sendResponseHeaders(HttpStatus.SC_FORBIDDEN, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public static void sendStatusOk(HttpExchange t, String response) throws IOException {
        t.sendResponseHeaders(HttpStatus.SC_OK, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public static String generatePageResponse(String userName, UserRoles role) {
        MustachePageDTO dto = new MustachePageDTO();
        dto.setUserName(userName);
        dto.setPage(role.toString());
        return MustacheCompiler.generate(dto, "html/globalPage.html");
    }
}
