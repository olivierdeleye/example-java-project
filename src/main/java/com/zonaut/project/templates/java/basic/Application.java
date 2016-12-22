package com.zonaut.project.templates.java.basic;

import com.zonaut.project.templates.java.basic.configuration.DatabaseConfiguration;
import com.zonaut.project.templates.java.basic.models.user.User;
import com.zonaut.project.templates.java.basic.tasks.ShutdownWebServerTask;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.tools.Server;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author David Debuck
 * @since 05/12/2016
 */
public class Application {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        logger.info("Application has started ...");
        logger.warn("Application has started ...");
        logger.error("Application has started ...");
        logger.debug("Application has started ..."); // Will not show as log level is set to INFO

        // Load config
        try {
            loadYamlFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Load data into the h2 memory database
        try {
            loadLiquibase();
        } catch (LiquibaseException e) {
            e.printStackTrace();
        }

        // Start simple server and shut it down after 60 seconds
        try {

            Server webServer = Server.createWebServer("-webAllowOthers","-webPort","3001").start();
            logger.info("Webserver started on port: 3001");

            // Do some actions on the database
            testData();

            logger.info("Webserver is still running on port 3001.");
            ShutdownWebServerTask shutdownWebServerTask = new ShutdownWebServerTask(webServer, 2000);
            shutdownWebServerTask.run();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void loadYamlFile() throws FileNotFoundException {
        Yaml yaml = new Yaml();
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("application.yml");
        Object object = yaml.load(inputStream);

        Map<String, Map<String, String>> values = (Map<String, Map<String, String>>) object;

        for (String key : values.keySet()) {
            Map<String, String> subValues = values.get(key);
            logger.info("KEY: {}", key);

            for (String subValueKey : subValues.keySet()) {
                logger.info("Sub key: {} => value: {}", subValueKey, subValues.get(subValueKey));
            }
        }
    }

    private static void loadLiquibase() throws LiquibaseException {
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(DatabaseConfiguration.getConnection()));
        Liquibase liquibase = new liquibase.Liquibase("liquibase/changelog/db.changelog-master.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());
    }

    private static void testData() {

        logger.info("Check database for users and map them ...");

        Connection connection = DatabaseConfiguration.getConnection();
        PreparedStatement selectPreparedStatement = null;

        // init with 1000 as we know how many there will be
        List<User> users = new ArrayList<>(1000);

        try {

            // Get Users
            String SelectQuery = "SELECT * FROM users";
            selectPreparedStatement = connection.prepareStatement(SelectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setCreatedDate(rs.getTimestamp("created_date").toInstant());
                user.setLastModifiedDate(rs.getTimestamp("last_modified_date").toInstant());
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                logger.info(user.toString());
                users.add(user);
            }

            selectPreparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        logger.info("Users found in database: {}", users.size());
        users = null;

    }

}