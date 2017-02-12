package database.helper.executor;

import database.helper.Connector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс, выполняющий переданные в параметрах методов SQL команды к базе данных,
 * соединение к которой также передано в параметрах.
 */
public final class SQLExecutor {

    /**
     * Метод выполняет SQL команду обновления базы (не возвращает результат в виде множеста).
     *
     * @param sql        команда, которую необходимо выполнить
     * @param connection соединение к базе данных, к которой необходимо выполнить запрос
     * @return количество измененных строк
     */
    public static int executeUpdate(String sql, Connection connection) throws SQLException {
        int updatedRowsCount;
        try (Statement statement = connection.createStatement()) {
            updatedRowsCount = statement.executeUpdate(sql);
        }
        return updatedRowsCount;
    }

    /**
     * Выполняет SQL команду вставки записи и возвращает номер вставленной записи
     * @param sql SQL команда вставки записи
     * @param connection соединение с БД
     * @return id вставленной строки
     */
    public static long executeInsert(String sql, Connection connection) throws SQLException {
        long insertedId;
        try (Statement stmt = connection.createStatement()) {
            insertedId = stmt.executeLargeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        }
        return insertedId;
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

    /**
     * Метод выполняющий транзакцию с соблюдением всех свойств транзакций.
     *
     * @param transactionBody транзакция
     * @param <T>             тип результата выполнения транзакции
     * @return результат выполнения команд в транзакции
     */
    public static <T> T executeTransaction(TransactionBody<T> transactionBody) throws SQLException {
        Connection connection = Connector.getConnection();
        try {
            connection.setAutoCommit(false);
            T result = transactionBody.call();
            connection.commit();
            return result;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {
            }
            throw e;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }
}
