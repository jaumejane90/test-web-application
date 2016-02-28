package com.jaume.constants;


public interface HttpServer {

    interface Session {
        String USERNAME_LOGED_ATTRIBUTE = "userNameLogged";
        String SESSION_TOKEN_ATTRIBUTE = "sessionToken";
    }

    interface Method {
        String GET = "GET";
        String POST = "POST";
        String PUT = "PUT";
        String DELETE = "DELETE";

        interface GET_DATA {
            String PARAMETERS_MAP = "parameters";
        }
    }

    interface Authenticator {
        String DEFAULT_MESSAGE = "";
    }

    interface Path {
        String LOGIN_PATH = "/login";
        String HOME_PATH = "/home";
        String LOGOUT_PATH = "/logout";
        String AUTHENTICATE_PATH = "/authenticate";
        String PAGE_1_PATH = "/page_1";
        String PAGE_2_PATH = "/page_2";
        String PAGE_3_PATH = "/page_3";
        String USER_PATH = "/user";
    }
}
