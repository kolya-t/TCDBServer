package database.helper.executor;

import java.sql.SQLException;
import java.util.concurrent.Callable;

public interface JDBCTransactionBody<T> extends Callable<T> {

    @Override
    T call() throws SQLException;
}
