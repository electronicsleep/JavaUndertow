package com.imgidea.java_undertow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;
import java.util.List;
import java.util.Arrays;


public class ServiceConfig {

    private static final Logger logger = LogManager.getLogger("java_undertow");

    public String getConfig(String propertyName)
    {
        logger.info("getConfig: " + propertyName);
        String propertyValue = "";

        List<String> propertyList = Arrays.asList("application.properties", "overrides.properties");

        for (int i = 0; i < propertyList.size(); i++) {

            String currentProperty = propertyList.get(i);
            logger.debug("currentProperty " + currentProperty);

            try (InputStream input = ServiceConfig.class.getClassLoader().getResourceAsStream(currentProperty)) {
                Properties prop = new Properties();
                prop.load(input);
                propertyValue = prop.getProperty(propertyName);

            } catch (Exception ignore) {
                logger.debug("property file not found " + currentProperty + " continue");
            }
        }

        if (System.getenv(propertyName) != null) {
            logger.debug("getenv overwrite: " + propertyValue);
            propertyValue = System.getenv(propertyName);
        }

        if (propertyName.contains("password") && propertyValue != null) {
            logger.debug("getConfig: " + propertyName + " value: masked");
        } else {
            logger.debug("getConfig: " + propertyName + " value: " + propertyValue);
        }

        return propertyValue;
    }
}
