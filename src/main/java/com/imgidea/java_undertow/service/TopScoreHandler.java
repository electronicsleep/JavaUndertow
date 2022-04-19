package com.imgidea.java_undertow.service;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Deque;
import java.util.Map;

public class TopScoreHandler implements HttpHandler {

    private final String content;
    private static final Logger logger = LogManager.getLogger("JavaUndertow");

    public TopScoreHandler(String content) {
        this.content = content;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        Map<String, Deque<String>> params = exchange.getQueryParameters();
        String name = params.get("name").getFirst();
        String score = params.get("score").getFirst();
        logger.info("TopScoreHandler Name: " + name + " Score:" + score + " Content: " + content);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        exchange.getResponseSender().send("{\""+name+"\": \""+score+"\"}");
    }
}
