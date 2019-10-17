package com.java_undertow;

import io.undertow.Undertow;
import static io.undertow.Handlers.path;
import io.undertow.util.Headers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {

    private static final Logger logger = LogManager.getLogger("JavaUndertow");

	public static void main(String[] args){
		Undertow.Builder builder = Undertow.builder();
		builder.setIoThreads(2);
		builder.setWorkerThreads(10);
        String host = "0.0.0.0";
        int port = 8080;
		builder.addHttpListener(port, host);
		builder.setHandler(path()
			.addPrefixPath("/", exchange -> {
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
				exchange.getResponseSender().send("Root endpoint");
                logger.info("Root endpoint");
			})
		);
		builder.setHandler(path()
				.addPrefixPath("/api", exchange -> {
					exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
					exchange.getResponseSender().send("API endpoint");
					logger.info("API endpoint");
				})
		);
		Undertow server = builder.build();
		server.start();
	}
}
