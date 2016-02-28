import spock.lang.Specification

class ContextTest extends Specification {
    def "Should be able to load the application context"() {
        when:
        Application.main()

        then: "No error means working fine"
        true
    }


}
