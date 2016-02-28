package constants;


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
}
