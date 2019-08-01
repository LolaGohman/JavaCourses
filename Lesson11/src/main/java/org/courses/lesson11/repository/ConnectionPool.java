package org.courses.lesson11.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {

    private ConnectionPool() {

    }

    private static ConnectionPool instance = null;

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private BasicDataSource ds = new BasicDataSource();

    {
        ds.setUrl(getDatabaseProperty("spring.datasource.url"));
        ds.setUsername(getDatabaseProperty("spring.datasource.username"));
        ds.setPassword(getDatabaseProperty("spring.datasource.password"));
        ds.setDriverClassName(getDatabaseProperty("spring.datasource.driver-class-name"));
    }


    Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private String getDatabaseProperty(String property) {
        try (
                InputStream inputStream = Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("application.properties")
        ) {
            Properties applicationProperties = new Properties();
            if (inputStream != null) {
                applicationProperties.load(inputStream);
                return applicationProperties.getProperty(property);
            } else {
                throw new FileNotFoundException("Resource not found!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
