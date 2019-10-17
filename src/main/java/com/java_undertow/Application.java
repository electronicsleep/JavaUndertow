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
				.addPrefixPath("/", exchange -> {
					exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
					exchange.getResponseSender().send("Root endpoint");
					logger.info("Root endpoint");
	            }).addPrefixPath("/api", exchange -> {
					exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
					exchange.getResponseSender().send("API endpoint");
					logger.info("API endpoint");
				}).addPrefixPath("/health", exchange -> {
					exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
					exchange.getResponseSender().send("Health endpoint");
					logger.info("Health endpoint");
				})
		);
		Undertow server = builder.build();
		server.start();
	}
}
