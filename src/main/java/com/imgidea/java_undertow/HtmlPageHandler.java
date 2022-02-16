package com.imgidea.java_undertow;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.imgidea.java_undertow.Constants.*;

public class HtmlPageHandler implements HttpHandler {

    private final String value;

    private static final Logger logger = LogManager.getLogger("JavaUndertow");

    public HtmlPageHandler(String value) {
        this.value = value;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        logger.info("Endpoint: " + value);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        exchange.getResponseSender().send(HTML_HEADER + value + HTML_FOOTER);
    }
}
