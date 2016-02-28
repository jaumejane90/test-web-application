package service;


public interface SessionService {

    String getUserNameOfSession(String sessionToken);

    void newSession(String sessionToken, String userName);

    void invalidate(String sessionToken);
}
