package services;


/**
 * Исключение, выбрасываемое методами AccountService'а
 */
public class AccountServiceException extends Exception {
    public AccountServiceException(Throwable cause) {
        super(cause);
    }
}
