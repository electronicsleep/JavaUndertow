package com.imgidea.java_undertow.service;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EventsRepo {

    private static final Logger logger = LogManager.getLogger("java_undertow");

    public String AddEvent(String datasource_connection, String datasource_password, String datasource_user, String service, String event, String event_type, String datetime) {
        logger.info("AddEvent: service " + service + " event " + event + " event_type" + event_type + " datetime " + datetime);
        String result = "";

        logger.info("datasource_connection:" + datasource_connection);

        if (datasource_connection != null) {

            try (Connection connection = DriverManager.getConnection(datasource_connection, datasource_user, datasource_password)) {
                if (service == null) {
                    service = "service";
                }
                logger.info("Database connected");
                Statement statement = null;
                String query = "";
                PreparedStatement stmt = null;
                int insertResult;

                if (datetime == null) {
                    query = "INSERT INTO events (service, event, event_type, datetime) values (?, ?, ?, NOW())";
                    stmt = connection.prepareStatement(query);
                    stmt.setString(1, service);
                    stmt.setString(2, event);
                    stmt.setString(3, event_type);
                    insertResult = stmt.executeUpdate();
                    logger.info(query);
                    result = "Inserting event row";
                    logger.debug("Executed query insertResult" + insertResult);
                } else {
                    query = "SELECT * FROM events WHERE service=? AND event=? AND datetime=?";
                    logger.info(query);
                    stmt = connection.prepareStatement(query);

                    stmt.setString(1, service);
                    stmt.setString(2, event);
                    stmt.setString(3, datetime);

                    ResultSet rs = stmt.executeQuery();
                    int insert_record = 0;

                    while (rs.next()) {
                        insert_record = 1;
                        logger.info("Not adding event, event already exists");
                        return "Duplicate Event";
                    }
                    query = "INSERT INTO events (service, event, event_type, datetime) values (?, ?, ?, ?)";
                    stmt = connection.prepareStatement(query);
                    stmt.setString(1, service);
                    stmt.setString(2, event);
                    stmt.setString(3, event_type);
                    stmt.setString(4, datetime);
                    insertResult = stmt.executeUpdate();
                    logger.info(query);
                    result = "Inserting event";

                    logger.info("Executed insertResult" + insertResult);
                }

            } catch (SQLException e) {
                logger.info("ERROR inserting event" + e);
                result = "ERROR inserting";
            }
        } else {
            result = "No database configured";
        }
        return result;
    }

    public List<String> SelectEvents(String datasource_connection, String datasource_password, String datasource_user) {
        logger.debug("GetALLEvents: datasource_connection: " + datasource_connection);

        List<String> eventsList = new ArrayList<>();

        if (datasource_connection != null) {
            logger.info("Connecting database...");

            try (Connection connection = DriverManager.getConnection(datasource_connection, datasource_user, datasource_password)) {
                logger.info("Database connected");
                Statement statement = null;
                statement = connection.createStatement();
                String query = "SELECT * FROM events ORDER BY datetime desc LIMIT 10";
                ResultSet rs = statement.executeQuery(query);

                while (rs.next()) {
                    int id = rs.getInt("event_id");
                    String ids = Integer.toString(id);
                    String service = rs.getString("service");
                    String event = rs.getString("event");
                    String event_type = rs.getString("event_type");
                    Date datetime = rs.getTimestamp("datetime");
                    eventsList.add(ids + " " + service + " " + event + " " + event_type + " " + datetime);
                }
                statement.close();
                return eventsList;
            } catch (SQLException e) {
                throw new IllegalStateException("ERROR: SQL Query failed" + e);
            }

        } else {
            logger.error("ERROR: No database endpoint configured");
        }
        return null;
    }

    public List<String> SearchEvents(String datasource_connection, String datasource_password, String datasource_user, String search) {
        logger.info("SearchEvents");
        logger.info("search: " + search);
        logger.info("datasource_connection: " + datasource_connection);

        if (datasource_connection == null) {
            logger.info("Connecting database...");

            try (Connection connection = DriverManager.getConnection(datasource_connection, datasource_user, datasource_password)) {
                PreparedStatement stmt = null;

                List<String> eventsList = new ArrayList<>();

                logger.info("Database connected");
                String query = "SELECT * FROM events where event LIKE ? order by datetime desc";
                search = "%" + search + "%";
                stmt = connection.prepareStatement(query);
                stmt.setString(1, search);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("event_id");
                    String ids=Integer.toString(id);
                    String service = rs.getString("service");
                    String event = rs.getString("event");
                    String event_type = rs.getString("event_type");
                    Date datetime = rs.getTimestamp("datetime");
                    eventsList.add(ids + " " + service + " " + event + " " + event_type + " " + datetime);
                }
                stmt.close();
                return eventsList;
            } catch (SQLException e) {
                throw new IllegalStateException("ERROR SQL Query failed" + e);
            }

        } else {
            logger.error("ERROR: no database endpoint configured");
        }
        return null;
    }
}
