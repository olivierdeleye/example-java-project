package com.zonaut.project.templates.java.basic.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author David Debuck
 * @since 22/12/2016
 */
public class DatabaseConfiguration {

    //private static final Logger logger = LogManager.getLogger();

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private static DataSource datasource;

    private static DataSource getDataSource() {
        if(datasource == null) {
            HikariConfig config = new HikariConfig();

            config.setDriverClassName(DB_DRIVER);
            config.setJdbcUrl(DB_CONNECTION);
            config.setUsername(DB_USER);
            config.setPassword(DB_PASSWORD);

            config.setMaximumPoolSize(10);
            config.setAutoCommit(false);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            datasource = new HikariDataSource(config);
        }
        return datasource;
    }

    public static Connection getConnection() {

        Connection connection = null;
        try {
            connection = getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;

    }

}
