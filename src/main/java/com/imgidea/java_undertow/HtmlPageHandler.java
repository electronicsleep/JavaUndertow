
package com.imgidea.java_undertow;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HtmlPageHandler implements HttpHandler {

    private final String value;

    final static String html_header = "<!DOCTYPE html>\n<html lang=\"en\">\n<body>" +
            "<a href=\"/\">Home</a> <a href=\"/status\">Status</a><br>";
    final static String html_footer = "</body>\n</html>";

    private static final Logger logger = LogManager.getLogger("JavaUndertow");

    public HtmlPageHandler(String value) {
        this.value = value;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        logger.info("Endpoint: " + value);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        exchange.getResponseSender().send(html_header + value + html_footer);
    }
}
