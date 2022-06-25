package com.imgidea.java_undertow.service;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.imgidea.java_undertow.ServiceConfig;

import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class AddEventService implements HttpHandler {

    private final String content;
    private static final Logger logger = LogManager.getLogger("JavaUndertow");

    public AddEventService(String content) {
        this.content = content;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        logger.info("AddEventService");
        ServiceConfig serviceConfig = new ServiceConfig();
        String datasource_connection = serviceConfig.getConfig("datasource_connection");
        String datasource_password = serviceConfig.getConfig("datasource_password");
        String datasource_user = serviceConfig.getConfig("datasource_user");

        Map<String, Deque<String>> queryParams = exchange.getQueryParameters();
        Deque<String> service_param = queryParams.get("service");
        String service = service_param.getFirst();

        Deque<String> event_param = queryParams.get("event");
        String event = event_param.getFirst();

        Deque<String> event_type_param = queryParams.get("event_type");
        String event_type = event_type_param.getFirst();

        logger.info("AddEventService: Service" + service + " Event:" + event + " EventType: " + event_type);

        List<String> eventsList;
        EventsRepo eventsRepo = new EventsRepo();
        eventsList = Collections.singletonList(eventsRepo.AddEvent(datasource_connection, datasource_password, datasource_user, service, event, event_type, null));

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
