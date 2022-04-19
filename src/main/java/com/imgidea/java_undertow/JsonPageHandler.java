package com.imgidea.java_undertow;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonPageHandler implements HttpHandler {

    private final String content;
    private static final Logger logger = LogManager.getLogger("JavaUndertow");

    public JsonPageHandler(String content) {
        this.content = content;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        logger.info("Endpoint: Json: " + content);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        exchange.getResponseSender().send(content);
    }
}
