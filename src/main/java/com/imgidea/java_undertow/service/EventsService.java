package com.imgidea.java_undertow.service;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.imgidea.java_undertow.ServiceConfig;

import java.util.List;

public class EventsService implements HttpHandler {

    private final String content;
    private static final Logger logger = LogManager.getLogger("JavaUndertow");

    public EventsService(String content) {
        this.content = content;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        logger.info("Select Events");
        ServiceConfig serviceConfig = new ServiceConfig();
        String datasource_connection = serviceConfig.getConfig("datasource_connection");
        String datasource_password = serviceConfig.getConfig("datasource_password");
        String datasource_user = serviceConfig.getConfig("datasource_user");

        List<String> eventsList;
        EventsRepo eventsRepo = new EventsRepo();
        eventsList = eventsRepo.SelectEvents(datasource_connection, datasource_password, datasource_user);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(eventsList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        exchange.getResponseSender().send(jsonString);
   }
}
