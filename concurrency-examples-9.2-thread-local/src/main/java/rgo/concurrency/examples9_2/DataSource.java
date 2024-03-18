package rgo.concurrency.examples9_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private final ThreadLocal<Connection> connectionHolder;

    public DataSource(String dbUrl) {
        connectionHolder = ThreadLocal.withInitial(() -> {
            try {
                return DriverManager.getConnection(dbUrl);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Connection acquireConnection() {
        return connectionHolder.get();
    }

    public void releaseConnection() {
        connectionHolder.remove();
    }
}
