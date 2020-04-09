package com.java_undertow;

import io.undertow.Undertow;
import static io.undertow.Handlers.path;
import io.undertow.util.Headers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {

    private static final Logger logger = LogManager.getLogger("JavaUndertow");

	public static void main(String[] args) {
		Undertow.Builder builder = Undertow.builder();
		builder.setIoThreads(2);
		builder.setWorkerThreads(10);
		String host = "0.0.0.0";
		int port = 8080;
		builder.addHttpListener(port, host);
		System.out.println("Server started: http://" + host + ":" + port);
		builder.setHandler(path()
				.addPrefixPath("/test", exchange -> {
					logger.info("test endpoint");
					exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
					exchange.getResponseSender().send("test endpoint");
	            }).addPrefixPath("/api", exchange -> {
					logger.info("API endpoint");
					exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
					exchange.getResponseSender().send("{\"status\": \"up\"}");
				}).addPrefixPath("/health", exchange -> {
					logger.info("Health endpoint");
					exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
					exchange.getResponseSender().send("{\"status\": \"up\"}");
				})
		);
		Undertow server = builder.build();
		server.start();
	}
}
