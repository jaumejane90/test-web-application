package view.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import constants.HttpServer;

public class ParameterFilter extends Filter {

    public static final String FILE_ENCODING = "file.encoding";
    public static final String EQUALS = "[=]";
    public static final String AND = "[&]";

    @Override
    public String description() {
        return "Parses the requested URI for parameters";
    }

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        parseGetParameters(exchange);
        chain.doFilter(exchange);
    }

    void parseGetParameters(HttpExchange exchange) throws UnsupportedEncodingException {
        Map<String, Object> parameters = new HashMap<>();
        URI requestedUri = exchange.getRequestURI();
        String query = requestedUri.getRawQuery();
        parseQuery(query, parameters);
        exchange.setAttribute(HttpServer.Method.GET_DATA.PARAMETERS_MAP, parameters);
    }

    void parseQuery(String query, Map<String, Object> parameters) throws UnsupportedEncodingException {
        if (query != null) {
            String pairs[] = query.split(AND);
            parseQueryPairs(parameters, pairs);
        }
    }

    void parseQueryPairs(Map<String, Object> parameters, String[] pairs) throws UnsupportedEncodingException {
        if (pairs != null) {
            for (String pair : pairs) {
                addPairToParameters(parameters, pair);
            }
        }
    }

    void addPairToParameters(Map<String, Object> parameters, String pair) throws UnsupportedEncodingException {
        String param[] = pair.split(EQUALS);
        String key = null;
        String value = null;
        if (param.length > 0) {
            key = URLDecoder.decode(param[0], System.getProperty(FILE_ENCODING));
        }

        if (param.length > 1) {
            value = URLDecoder.decode(param[1], System.getProperty(FILE_ENCODING));
        }
        parameters.put(key, value);
    }
}