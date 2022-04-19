package com.imgidea.java_undertow;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.imgidea.java_undertow.service.StatusPageHandler;
import com.imgidea.java_undertow.service.TopScoreHandler;

public class RoutingHandlers {

    public static HttpHandler HtmlPageHandler(String value) {
        return new HtmlPageHandler(value);
    }
    public static HttpHandler JsonPageHandler(String value) {
        return new JsonPageHandler(value);
    }

    public static HttpHandler StatusPageHandler(String value) {
        return new StatusPageHandler(value);
    }

    public static HttpHandler TopScoreHandler(String value) {
        return new TopScoreHandler(value);
    }

    public static void notFoundHandler(HttpServerExchange exchange) {
        exchange.setStatusCode(404);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        exchange.getResponseSender().send("Page Not Found");
    }
}
