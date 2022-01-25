package com.imgidea.java_undertow.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class StatusPage {

    private static final Logger logger = LogManager.getLogger("java_undertow");

    final static String ColorGreen = "#00FF00";
    final static String ColorYellow = "#FFFF99";
    final static String ColorRed = "#FF0000";
    final static String Section = "-----";

    public String Monitor() {
        String SectionName = "Start";
        String charts = "\n<table>";
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
                        logger.info("Monitor Warn Found: " + CurrentLine);
                    }
                    if (CurrentLine.indexOf("Error:") >= 0) {
                        ErrorFound = true;
                        IssueReason = "<br>" + CurrentLine;
                        logger.info("Monitor Error Found: " + CurrentLine);
                    }
                }
            }
            br.close();
            charts += "\n</table>\n";
            logger.info("status endpoint");
        } catch(IOException e) {
            e.printStackTrace();
        }
        return charts;
    }
}
