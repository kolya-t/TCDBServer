package dbservice.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {

    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public int executeUpdate(String sql) throws SQLException {
        int updated;
        try (Statement statement = connection.createStatement()) {
            updated = statement.executeUpdate(sql);
        }
        return updated;
    }

    public <T> T executeQuery(String sql, ResultHandler<T> handler) throws SQLException {
        T value;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql))
        {
            value = handler.handle(resultSet);
        }
        return value;
    }
}
