package com.jaume.service.impl

import spock.lang.Specification


class GuavaCacheSessionServiceTest extends Specification {
    private GuavaCacheSessionService guavaCacheSessionService;

    void setup() {
        guavaCacheSessionService = new GuavaCacheSessionService();

    }

    def "Should contain sessionToken after newSession(sessionToken)"() {
        given:
        String sessionToken = "token"
        String userName = "userName"
        when:
        guavaCacheSessionService.newSession(sessionToken, userName)
        then:
        guavaCacheSessionService.getUserNameOfSession(sessionToken) == userName
    }

    def "Should not contain sessionToken after newSession(sessionToken) and invalidate(sessionToken)"() {
        given:
        String sessionToken = "token"
        String userName = "userName"
        when:
        guavaCacheSessionService.newSession(sessionToken, userName)
        guavaCacheSessionService.invalidate(sessionToken)
        then:
        guavaCacheSessionService.getUserNameOfSession(sessionToken) == null
    }
}
