package com.imgidea.java_undertow;

import com.imgidea.java_undertow.service.StatusPage;

import io.undertow.Undertow;
import static io.undertow.Handlers.path;
import io.undertow.util.Headers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {

    private static final Logger logger = LogManager.getLogger("JavaUndertow");

	final static String html_header = "<!DOCTYPE html>\n<html lang=\"en\">\n<body><a href=\"/\">home</a> <a href=\"/status\">status</a><br>\n";
	final static String html_footer = "</body>\n</html>";

	final static int IoThreads = 5;
	final static int WorkerThreads = 10;

	final static String host = "0.0.0.0";
	final static int port = 8080;

	public static void main(String[] args) {
		Undertow.Builder builder = Undertow.builder();
		builder.setIoThreads(IoThreads);
		builder.setWorkerThreads(WorkerThreads);
		builder.addHttpListener(port, host);
		System.out.println("Server: http://" + host + ":" + port);
		builder.setHandler(path()
				.addPrefixPath("/", exchange -> {
					logger.info("Root endpoint");
					logger.info("Debug Root endpoint");
					exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
					exchange.getResponseSender().send(html_header + "JavaUndertow" + html_footer);
	            }).addPrefixPath("/api", exchange -> {
					logger.info("API endpoint");
					exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
					exchange.getResponseSender().send("{\"api\": \"Up\"}");
				}).addPrefixPath("/status", exchange -> {
					logger.info("Status endpoint");
					StatusPage statusPage = new StatusPage();
					String content = statusPage.Monitor();
					exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
					exchange.getResponseSender().send(html_header + content + html_footer);
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
