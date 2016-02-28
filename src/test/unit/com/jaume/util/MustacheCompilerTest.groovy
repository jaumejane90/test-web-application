package com.jaume.util

import spock.lang.Specification


class MustacheCompilerTest extends Specification {

    def "Generate"() {
        given:
            MustachePageDTO dto = new MustachePageDTO();
            dto.setUserName("test")
        when:
            String result = MustacheCompiler.generate(dto,"html/globalPage.html")
        then:
            result.contains("Hello test")
    }
}
