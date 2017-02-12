package database.helper.executor;

import java.sql.SQLException;
import java.util.concurrent.Callable;

public interface TransactionBody<T> extends Callable<T> {

    @Override
    T call() throws SQLException;
}
