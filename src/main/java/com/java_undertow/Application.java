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
		builder.setIoThreads(5);
		builder.setWorkerThreads(10);
		String host = "0.0.0.0";
		int port = 8080;
		builder.addHttpListener(port, host);
		System.out.println("Server started: http://" + host + ":" + port);
		builder.setHandler(path()
				.addPrefixPath("/", exchange -> {
					logger.info("Root endpoint");
					exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
					exchange.getResponseSender().send("root endpoint");
	            }).addPrefixPath("/api", exchange -> {
					logger.info("API endpoint");
					ScoreApi scoreApi = new ScoreApi();
					scoreApi.test();
					exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
					exchange.getResponseSender().send("{\"api\": \"Up\"}");
				}).addPrefixPath("/health", exchange -> {
					logger.info("Health endpoint");
					exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
					exchange.getResponseSender().send("{\"status\": \"Up\"}");
				})
		);
		Undertow server = builder.build();
		server.start();
	}
}
