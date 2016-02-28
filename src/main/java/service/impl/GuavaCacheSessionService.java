package service.impl;


import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import service.SessionService;

public class GuavaCacheSessionService implements SessionService {
    public static final int CACHE_SIZE = 10000;
    public static final int EXPIRATION_TIME = 5;
    Cache<String, String> sessions;

    public GuavaCacheSessionService() {
        sessions = CacheBuilder.newBuilder()
                .maximumSize(CACHE_SIZE)
                .expireAfterAccess(EXPIRATION_TIME, TimeUnit.MINUTES)
                .build();
    }

    public String getUserNameOfSession(String sessionToken) {
        if (sessionToken == null) return null;
        return sessions.getIfPresent(sessionToken);
    }

    public void newSession(String sessionToken, String userName) {
        sessions.put(sessionToken, userName);
    }

    public void invalidate(String sessionToken) {
        sessions.invalidate(sessionToken);
    }
}
