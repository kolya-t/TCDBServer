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
        Statement statement = connection.createStatement();
        int updated = statement.executeUpdate(sql);
        statement.close();
        return updated;
    }

    public <T> T executeQuery(String sql, ResultHandler<T> handler) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        T handled = handler.handle(resultSet);
        resultSet.close();
        statement.close();
        return handled;
    }
}
