package com.jaume.view.filter

import spock.lang.Specification

class ParameterFilterTest extends Specification {

    private ParameterFilter parameterFilter;

    void setup() {
        parameterFilter = new ParameterFilter();
    }

    def "ParseQuery"() {
        given:
        Map<String, Object> parameters = new HashMap<>();
        String query = "key=value&key2=value2";

        when:
        parameterFilter.parseQuery(query, parameters);

        then:
        parameters.containsKey("key") == true
        parameters.get("key") == "value"
        parameters.containsKey("key2") == true
        parameters.get("key2") == "value2"
    }

    def "IfPairsContainsValuesShouldMapToParameters"() {
        given:
        Map<String, Object> parameters = new HashMap<>();
        String[] pairs = ["key=value", "key2=value2"];

        when:
        parameterFilter.parseQueryPairs(parameters, pairs);

        then:
        parameters.containsKey("key") == true
        parameters.get("key") == "value"
        parameters.containsKey("key2") == true
        parameters.get("key2") == "value2"
    }

    def "IfPairsIsNullShouldMapToParameters"() {
        given:
        Map<String, Object> parameters = new HashMap<>();
        String[] pairs = null;

        when:
        parameterFilter.parseQueryPairs(parameters, pairs);

        then:
        parameters.size() == 0
    }

    def "AddPairToParameters"() {
        given:
        Map<String, Object> parameters = new HashMap<>();
        String pair = "key=value";

        when:
        parameterFilter.addPairToParameters(parameters, pair);

        then:
        parameters.containsKey("key") == true
        parameters.get("key") == "value"
    }
}
