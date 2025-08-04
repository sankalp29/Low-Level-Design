package DBConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class DBConnectionPool {
    private static volatile DBConnectionPool instance;
    private static final int POOL_SIZE = 10;
    private final BlockingQueue<Connection> connectionPool;

    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String URL = "jdbc:mysql//localhost:3306/mydb";

    private DBConnectionPool() {
        connectionPool = new LinkedBlockingDeque();
        initializePool();
    }

    public static DBConnectionPool getInstance() {
        if (instance == null) {
            synchronized (DBConnectionPool.class) {
                if (instance == null) instance = new DBConnectionPool();
            }
        }

        return instance;
    }

    public Connection getConnection() throws InterruptedException {
        return connectionPool.take();   
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) connectionPool.offer(connection);
    }

    private void initializePool() {
        for (int i = 1; i <= POOL_SIZE; i++) {
            try {
                Connection connection = DriverManager.getConnection(USER, PASSWORD, URL);
                connectionPool.offer(connection);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
