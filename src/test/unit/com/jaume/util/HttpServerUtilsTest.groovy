package com.jaume.util

import com.sun.net.httpserver.HttpExchange
import spock.lang.Specification

class HttpServerUtilsTest extends Specification {
    def "Should return session token attribute if exist"() {
        given:
            HttpExchange t = Mock(HttpExchange.class)
        t.getAttribute(com.jaume.constants.HttpServer.Session.SESSION_TOKEN_ATTRIBUTE) >> "token"
        when:
            def result = HttpServerUtils.getSessionToken(t)
        then:
            result == "token"
    }

    def "Should return null session token attribute if not exist"() {
        given:
        HttpExchange t = Mock(HttpExchange.class)
        t.getAttribute(com.jaume.constants.HttpServer.Session.SESSION_TOKEN_ATTRIBUTE) >> null
        when:
        def result = HttpServerUtils.getSessionToken(t)
        then:
        result == null
    }

    def "Should return username attribute if exist"() {
        given:
        HttpExchange t = Mock(HttpExchange.class)
        t.getAttribute(com.jaume.constants.HttpServer.Session.USERNAME_LOGED_ATTRIBUTE) >> "userName"
        when:
        def result = HttpServerUtils.getUserNameOfSession(t)
        then:
        result == "userName"
    }

    def "Should return null username attribute if not exist"() {
        given:
        HttpExchange t = Mock(HttpExchange.class)
        t.getAttribute(com.jaume.constants.HttpServer.Session.USERNAME_LOGED_ATTRIBUTE) >> null
        when:
        def result = HttpServerUtils.getUserNameOfSession(t)
        then:
        result == null
    }
}
