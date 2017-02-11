package services;

/**
 * Ошибка при выполнении операции с датабазным сервисом
 */
public class UserServiceException extends Exception {
    /**
     * При выбрасывании ошибки соединение с базой данных закрывается
     *
     * @param e
     */
    public UserServiceException(Exception e) {
        super(e);
    }
}
