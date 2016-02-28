package com.jaume.util;


public class MainUtils {
    static final int DEFAULT_PORT = 8080;

    public static int getDefaultPortOrArgsPort(String[] args) {
        return args != null && args.length > 0 ? getArgsPort(args[0]) : DEFAULT_PORT;
    }

    static int getArgsPort(String arg) {
        return arg != null ? Integer.parseInt(arg) : DEFAULT_PORT;
    }
}
