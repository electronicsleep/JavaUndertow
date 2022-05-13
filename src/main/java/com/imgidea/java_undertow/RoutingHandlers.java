package com.imgidea.java_undertow;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import com.imgidea.java_undertow.service.StatusPage;
import com.imgidea.java_undertow.service.TopScores;
//import com.imgidea.java_undertow.service.EventsService;

public class RoutingHandlers {

    public static HttpHandler HtmlPageHandler(String value) {
        return new HtmlPageHandler(value);
    }
    public static HttpHandler JsonPageHandler(String value) {
        return new JsonPageHandler(value);
    }

    public static HttpHandler StatusPageHandler(String value) {
        return new StatusPage(value);
    }

    public static HttpHandler TopScoreHandler(String value) {
        return new TopScores(value);
    }

    /*
    public static HttpHandler EventsHandler(String value) {
        return new EventsService(value);
    }
    */

    public static void notFoundHandler(HttpServerExchange exchange) {
        exchange.setStatusCode(404);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        exchange.getResponseSender().send("Page Not Found");
    }
}
