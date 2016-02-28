package com.jaume.view.handler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Map;

import org.apache.http.HttpStatus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jaume.constants.HttpServer;
import com.jaume.domain.User;
import com.jaume.service.UserService;
import com.jaume.util.HttpServerUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpPrincipal;


public class UserResourceHandler implements HttpHandler {
    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String UTF_8 = "UTF-8";
    public static final String USER_NAME_PARAM = "userName";
    private UserService userService;
    private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public UserResourceHandler(UserService userService) {
        this.userService = userService;
    }

    static String getStringFromHttpExchangeRequestBody(HttpExchange t) {
        String requestString = "";
        InputStream is;
        try {
            StringBuilder requestBuffer = new StringBuilder();
            is = t.getRequestBody();
            int rByte;
            while ((rByte = is.read()) != -1) {
                requestBuffer.append((char) rByte);
            }
            is.close();

            if (requestBuffer.length() > 0) {
                requestString = URLDecoder.decode(requestBuffer.toString(), UTF_8);
            } else {
                requestString = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestString;
    }

    public void handle(HttpExchange t) throws IOException {
        HttpPrincipal httpPrincipal = t.getPrincipal();
        String userName = httpPrincipal.getName();
        String requestMethod = t.getRequestMethod();
        boolean validOperation = userService.validateOperation(userName, requestMethod);

        if (validOperation) {
            executeOperation(t, requestMethod);
        } else {
            t.sendResponseHeaders(HttpStatus.SC_FORBIDDEN, -1L);
        }
    }

    void executeOperation(HttpExchange t, String requestMethod) throws IOException {
        if (HttpServer.Method.GET.equalsIgnoreCase(requestMethod)) {
            executeGetOperation(t);
        } else {
            handlePostDataMethod(t, requestMethod);
        }
    }

    void executeGetOperation(HttpExchange t) throws IOException {
        Map<String, String> getParams = (Map<String, String>) t.getAttribute(HttpServer.Method.GET_DATA.PARAMETERS_MAP);
        User user = userService.findByUserName(getParams.get(USER_NAME_PARAM));
        sendGetResponse(t, user);
    }

    private void sendGetResponse(HttpExchange t, User user) throws IOException {
        if (user == null) {
            t.sendResponseHeaders(HttpStatus.SC_NOT_FOUND, -1);
        } else {
            t.getResponseHeaders().add(CONTENT_TYPE_HEADER, APPLICATION_JSON);
            String response = gson.toJson(user);
            HttpServerUtils.sendStatusOk(t,response);
        }
    }

    void handlePostDataMethod(HttpExchange t, String requestMethod) throws IOException {
        int httpCodeResult;
        String requestString = getStringFromHttpExchangeRequestBody(t);
        User user = gson.fromJson(requestString, User.class);
        if (HttpServer.Method.POST.equalsIgnoreCase(requestMethod)) {
            userService.insert(user);
            httpCodeResult = HttpStatus.SC_CREATED;
        } else if (HttpServer.Method.PUT.equalsIgnoreCase(requestMethod)) {
            userService.update(user);
            httpCodeResult = HttpStatus.SC_NO_CONTENT;
        } else if (HttpServer.Method.DELETE.equalsIgnoreCase(requestMethod)) {
            userService.deleteByUserName(user.getUserName());
            httpCodeResult = HttpStatus.SC_NO_CONTENT;
        } else {
            httpCodeResult = HttpStatus.SC_NOT_FOUND;
        }
        t.sendResponseHeaders(httpCodeResult, -1);
    }
}
