package view.handler

import com.sun.net.httpserver.HttpExchange
import spock.lang.Specification

class UserResourceHandlerTest extends Specification {

    private UserResourceHandler userResourceHandler;

    void setup() {
        userResourceHandler = new UserResourceHandler();
    }

    def "GetJsonStringFromHttpExchange"() {
        given:
        HttpExchange t = Mock(HttpExchange.class)
        String requestBody = "{}"
        InputStream is = new ByteArrayInputStream(requestBody.getBytes());
        t.getRequestBody() >> is
        when:
        def result = userResourceHandler.getStringFromHttpExchangeRequestBody(t);
        then:
        result == "{}"
    }
}
