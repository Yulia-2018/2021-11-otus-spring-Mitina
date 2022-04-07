package ru.otus.homework.actuator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class HealthIndicatorBook implements HealthIndicator {

    private final String url;

    private final String username;

    private final String password;

    public HealthIndicatorBook(@Value("${spring.datasource.url}") String url,
                               @Value("${spring.datasource.username}") String username,
                               @Value("${spring.datasource.password}") String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Health health() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT 1 FROM book WHERE ROWNUM = 1");
                ps.executeQuery();
                return Health.up().status(Status.UP).withDetail("message", "Table book available").build();
            } catch (SQLException e) {
                e.printStackTrace();
                return Health.down().status(Status.DOWN).withDetail("message", "Table book: " + e.getLocalizedMessage()).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Health.down().status(Status.DOWN).withDetail("message", "Database: " + e.getLocalizedMessage()).build();
        }
    }
}
