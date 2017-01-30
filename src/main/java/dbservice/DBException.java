package dbservice;

import java.sql.SQLException;

/**
 * Ошибка при выполнении операции с датабазным сервисом
 */
public class DBException extends Exception {
    public DBException(SQLException e) {
        super(e);
    }
}
