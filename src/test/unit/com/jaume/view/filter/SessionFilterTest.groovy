package com.jaume.view.filter

import com.jaume.service.SessionService
import com.sun.net.httpserver.Filter
import com.sun.net.httpserver.Headers
import com.sun.net.httpserver.HttpExchange
import org.apache.http.HttpStatus
import spock.lang.Specification

class SessionFilterTest extends Specification {
    private SessionFilter sessionFilter;

    void setup() {
        sessionFilter = new SessionFilter();
    }

    def "GetSessionTokenFromCookie"() {
        expect:
        sessionToken == sessionFilter.getSessionTokenFromCookie(cookie);

        where:
        cookie          | sessionToken
        null            | null
        "session=token" | "token"
        "abc=bca"       | null

    }

    def "GetSessionTokenFromCookies"() {
        expect:
        sessionToken == sessionFilter.getSessionTokenFromCookies(cookie);

        where:
        cookie                                    | sessionToken
        null                                      | null
        Arrays.asList("session=token")            | "token"
        Arrays.asList("session=token", "abc=bca") | "token"
        Arrays.asList("abc=bca")                  | null
    }

    def "WhenSessionIsValidShouldCallNextChainFilter"() {
        given:
        SessionService sessionService = Mock(SessionService.class)
        sessionService.getUserNameOfSession("token") >> "user"
        sessionFilter = new SessionFilter(sessionService)

        Headers headers = new Headers();
        headers.put(sessionFilter.COOKIE_HEADER, Arrays.asList("session=token", "abc=bca"));
        HttpExchange t = Mock(HttpExchange.class);
        t.getRequestHeaders() >> headers

        Filter.Chain chain = Mock(Filter.Chain.class);
        when:
        sessionFilter.doFilter(t, chain);
        then:
        1 * chain.doFilter(_)
    }

    def "WhenSessionIsInValidShouldNotCallNextChainFilterAndShouldRedirect"() {
        given:
        SessionService sessionService = Mock(SessionService.class)
        sessionService.getUserNameOfSession("token") >> null
        sessionFilter = new SessionFilter(sessionService)

        Headers headers = new Headers();
        headers.put(sessionFilter.COOKIE_HEADER, Arrays.asList("session=token", "abc=bca"));
        HttpExchange t = Mock(HttpExchange.class);
        t.getRequestHeaders() >> headers
        t.getResponseHeaders() >> headers

        Filter.Chain chain = Mock(Filter.Chain.class);
        when:
        sessionFilter.doFilter(t, chain);
        then:
        0 * chain.doFilter(_)
        1 * t.sendResponseHeaders(HttpStatus.SC_MOVED_TEMPORARILY, -1L);
    }


}
