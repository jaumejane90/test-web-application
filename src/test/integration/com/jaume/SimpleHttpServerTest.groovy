package com.jaume

import com.jaume.constants.HttpServer
import com.jaume.service.SessionService
import com.jaume.view.filter.SessionFilter
import com.jaume.view.handler.HomeHandler
import com.jaume.view.handler.LoginHandler
import spock.lang.Specification

class SimpleHttpServerTest extends Specification {

    def "Should be able to handle request for specific path with handler"() {
        given:
        int port = 8082
        LoginHandler loginPageSpy = Spy(LoginHandler.class)
        SimpleHttpServer server = SimpleHttpServerBuilder.aSimpleHttpServer()
                .withPort(port)
                .withHandler(HttpServer.Path.LOGIN_PATH, loginPageSpy)
                .build()
        server.start()

        when:
        RestUtils.getUrl("http://localhost:" + port + HttpServer.Path.LOGIN_PATH)

        then:
        1 * loginPageSpy.handle(_)
    }

    def "Should be able to handle request for specific path with handler and filter"() {
        given:
        int port = 8083
        HomeHandler homeHandlerSpy = Spy(HomeHandler.class)
        SessionService sessionServiceMock = Mock(SessionService.class)
        SessionFilter sessionFilter = new SessionFilter(sessionServiceMock);
        SimpleHttpServer server = SimpleHttpServerBuilder.aSimpleHttpServer()
                .withPort(port)
                .withHandlerAndFilter(HttpServer.Path.HOME_PATH, homeHandlerSpy, sessionFilter)
                .build()
        server.start()

        when:
        RestUtils.getUrl("http://localhost:" + port + HttpServer.Path.HOME_PATH)

        then:

        1 * sessionServiceMock.getUserNameOfSession(_) >> ""
        1 * homeHandlerSpy.handle(_)
    }

}
