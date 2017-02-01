package database.helper.executor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Функциональный интерфейс, метод которого обрабатывает результат выполнения запроса к БД
 * и преобразует его в результат нужного вам формата.
 *
 * @param <T> тип данных, какого будет результат обработки {@link ResultSet}
 */
public interface ResultHandler<T> {

    /**
     * Метод обрабатывает результат выполнения запроса к БД и преобразует его в результат нужного вам формата.
     * @param resultSet то, что нужно обработать
     * @return результат обработки resultSet
     */
    T handle(ResultSet resultSet) throws SQLException;
}
