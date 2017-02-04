package database;

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
    }
}
