package com.imgidea.java_undertow;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.imgidea.java_undertow.service.StatusPageHandler;

public class RoutingHandlers {

    private static final Logger logger = LogManager.getLogger("JavaUndertow");

    public static HttpHandler HtmlPageHandler(String value) {
        logger.info("htmlPageHandler");
        return new HtmlPageHandler(value);
    }

    public static HttpHandler statusPageHandler(String value) {
        logger.info("StatusPageHandler");
        return new StatusPageHandler(value);
    }

    public static void notFoundHandler(HttpServerExchange exchange) {
        logger.info("notFoundHandler");
        exchange.setStatusCode(404);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        exchange.getResponseSender().send("Page Not Found");
    }
}
