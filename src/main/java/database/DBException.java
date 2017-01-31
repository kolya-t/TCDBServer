package database;

/**
 * Ошибка при выполнении операции с датабазным сервисом
 */
public class DBException extends Exception {
    public DBException(Exception e) {
        super(e);
    }
}
