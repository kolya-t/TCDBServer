package database.helper.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;

/**
 * Класс, выполняющий переданные в параметрах методов SQL команды к базе данных,
 * соединение к которой также передано в параметрах.
 */
public final class Executor {

    /**
     * Метод выполняет SQL команду обновления базы (не возвращает результат в виде множеста).
     *
     * @param sql        команда, которую необходимо выполнить
     * @param connection соединение к базе данных, к которой необходимо выполнить запрос
     * @return количество измененных строк
     */
    public static int executeUpdate(String sql, Connection connection) throws SQLException {
        int updated;
        try (Statement statement = connection.createStatement()) {
            updated = statement.executeUpdate(sql);
        }
        return updated;
    }

    /**
     * Метод выполняет SQL команду и возвращает результат операции в формате,
     * который определяется переданным в параметрах handler'ом.
     *
     * @param sql        команда, которую нужно выполнить
     * @param connection соединение с базой данных, к которйо выполняются запросы
     * @param handler    обработчик результата выполнения команды
     * @param <T>        тип данных, определяемый обработчиком результата {@link ResultHandler}
     * @return результат выполнения команды и обработки {@link ResultHandler}
     */
    public static <T> T executeQuery(String sql, Connection connection, ResultHandler<T> handler) throws SQLException {
        T value;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            value = handler.handle(resultSet);
        }
        return value;
    }
}
