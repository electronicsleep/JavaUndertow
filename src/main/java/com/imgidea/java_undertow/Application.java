package com.imgidea.java_undertow;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.imgidea.java_undertow.Constants.*;

public class Application {

    private static final Logger logger = LogManager.getLogger("JavaUndertow");

	final static int IoThreads = 5;
	final static int WorkerThreads = 10;
	final static String host = "0.0.0.0";
	final static int port = 8080;

	public static void main(String[] args) {
		System.out.println("Server: http://" + host + ":" + port);
		Undertow.Builder builder = Undertow.builder();
		builder.setIoThreads(IoThreads);
		builder.setWorkerThreads(WorkerThreads);

		Undertow server = Undertow.builder()
				.addHttpListener(port, host, ROUTES)
				.build();
		server.start();
		logger.info("Server Started");
	}

	private static HttpHandler ROUTES = new RoutingHandler()
		.get("/", RoutingHandlers.HtmlPageHandler("JavaUndertow"))
		.get("/about", RoutingHandlers.HtmlPageHandler(ABOUT))
		.get("/status", RoutingHandlers.StatusPageHandler("Status"))
		.get("/health", RoutingHandlers.JsonPageHandler(HEALTH_OK))
		.get("/add_score/{name}/{score}", RoutingHandlers.TopScoreHandler("test"))
		.post("/about", RoutingHandlers.HtmlPageHandler("About/POST"))
		.get("/page*", RoutingHandlers.HtmlPageHandler("Page*"))
		.setFallbackHandler(RoutingHandlers::notFoundHandler);
}
