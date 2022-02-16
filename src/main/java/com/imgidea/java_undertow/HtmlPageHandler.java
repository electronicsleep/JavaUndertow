package com.imgidea.java_undertow;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.imgidea.java_undertow.Constants.*;

public class HtmlPageHandler implements HttpHandler {

    private final String content;

    private static final Logger logger = LogManager.getLogger("JavaUndertow");

    public HtmlPageHandler(String content) {
        this.content = content;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        logger.info("Endpoint: " + content);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        exchange.getResponseSender().send(HTML_HEADER + content + HTML_FOOTER);
    }
}
