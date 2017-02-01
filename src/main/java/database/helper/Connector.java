package database.helper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс с единственным методом, создающим и возвращающим соединение с БД
 */
@SuppressWarnings("UnusedDeclaration")
public final class Connector {

    /**
     * Соединение к базе данных
     */
    private static Connection connection;

    /**
     * Метод создает соединение, если оно не было создано ранее или было закрыто, и возвращает его.
     * Либо возвращает созданное ранее незакрытое соединение.
     *
     * @return соединение к базе данных
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String jdbcDriverClassName = PropertyService.getInstance().getJDBCDriverClassName();
                Driver driver = (Driver) Class.forName(jdbcDriverClassName).newInstance();
                DriverManager.registerDriver(driver);

                String url = PropertyService.getInstance().getConnectionURL();
                connection = DriverManager.getConnection(url);
            }
        } catch (IOException | InstantiationException | IllegalAccessException | SQLException | ClassNotFoundException e) {
            System.err.println("Ошибка при создании соединения");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Пустой приватный конструктор. Нужен чтобы запретить пользователю создавать экземпляр класса.
     */
    private Connector() {
    }
}
