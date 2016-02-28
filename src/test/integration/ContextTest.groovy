import spock.lang.Specification

class ContextTest extends Specification {
    def "Should be able to load the application context"() {
        given:
        String[] args = ["8081"];
        when:
        Application.main(args)

        then: "No error means working fine"
        true
    }


}
