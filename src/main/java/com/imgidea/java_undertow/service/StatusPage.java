package com.imgidea.java_undertow.service;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.imgidea.java_undertow.Constants.*;

public class StatusPage implements HttpHandler {

    private static final Logger logger = LogManager.getLogger("java_undertow");

    final static String ColorGreen = "#00FF00";
    final static String ColorYellow = "#FFFF99";
    final static String ColorRed = "#FF0000";
    final static String Section = "-----";

    private final String content;

    public StatusPage(String content) {
        this.content = content;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        logger.info("Endpoint: StatusPageHandler Content: " + content);

        String SectionName = "Start";
        String charts = "<table>";
        String CurrentLine = "";
        String Color = "";

        boolean WarnFound = false;
        boolean ErrorFound = false;
        String IssueReason = "";
        String StatusFile = "status.log";

        try {
            File file = new File(StatusFile);

            if (file.createNewFile()) {
                URL url = new URL("http://localhost:8080/refresh");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                if (connection.getResponseCode() != 200) {
                    logger.error("problem pulling log file from s3");
                } else {
                    logger.error("pulled log file from s3");
                }
            }

            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((CurrentLine = br.readLine()) != null) {
                if (CurrentLine.indexOf(Section) == 0) {
                    if (ErrorFound) {
                        Color = ColorRed;
                    } else if (WarnFound) {
                        Color = ColorYellow;
                    } else {
                        Color = ColorGreen;
                    }

                    SectionName = SectionName.replace("-", "");
                    if (SectionName != "Start") {
                        charts += "\n <tr><td style=\"background-color:" + Color + ";\" width=\"200\">" +
                                SectionName + IssueReason + "</td></tr>";
                    }
                    SectionName = CurrentLine;
                    WarnFound = false;
                    ErrorFound = false;
                    IssueReason = "";
                } else {
                    if (CurrentLine.indexOf("Warn:") >= 0) {
                        WarnFound = true;
                        IssueReason = "<br>" + CurrentLine;
                        logger.debug("Monitor Warn Found: " + CurrentLine);
                    }
                    if (CurrentLine.indexOf("Error:") >= 0) {
                        ErrorFound = true;
                        IssueReason = "<br>" + CurrentLine;
                        logger.debug("Monitor Error Found: " + CurrentLine);
                    }
                }
            }
            br.close();
            charts += "\n</table>";
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
            exchange.getResponseSender().send(HTML_HEADER + HEADER_LINKS + charts + HTML_FOOTER);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
