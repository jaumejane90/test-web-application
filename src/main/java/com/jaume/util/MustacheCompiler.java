package com.jaume.util;

import java.io.IOException;
import java.io.StringWriter;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public class MustacheCompiler {
    public static String generate(Object backing, String template) {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(template);
        StringWriter result = new StringWriter();
        try {
            mustache.execute(result, backing).flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result.toString();
    }
}
