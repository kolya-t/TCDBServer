package database;

import database.helper.Connector;

import java.sql.SQLException;

/**
 * Ошибка при выполнении операции с датабазным сервисом
 */
public class DBException extends Exception {
    /**
     * При выбрасывании ошибки соединение с базой данных закрывается
     * @param e
     */
    public DBException(Exception e) {
        super(e);
        try {
            Connector.getConnection().close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
