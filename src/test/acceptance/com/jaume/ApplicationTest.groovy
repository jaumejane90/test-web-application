package com.jaume

import spock.lang.Specification

class ApplicationTest extends Specification {
    def "A user should be able to login"() {
        given:
        Application.main();
        when:
        def result = RestUtils.authenticate("http://localhost:8080/authenticate", "UserRoleAdmin", "123")
        then:
        result == true
    }
}
