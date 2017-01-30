package dbservice;

import dbservice.dao.UsersDao;
import dbservice.datasets.User;

import java.io.*;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;


public class DBService {
    private static final String CFG_FILENAME = "db\\db.properties";

    private final Connection connection;
    private static DBService instance;

    private DBService() throws DBException {
        try {
            registerMySQLDriver();
        } catch (SQLException e) {
            throw new DBException(e);
        }
        this.connection = getMySQLConnection();
    }

    public static DBService instance() throws DBException {
        if (instance == null) {
            instance = new DBService();
        }
        return instance;
    }

    /**
     * Регистрация MySQL JDBC драйвера
     */
    private static void registerMySQLDriver() throws SQLException {
        try {
            Driver driver = (Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            DriverManager.registerDriver(driver);
        } catch (SQLException | InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            System.err.println("Не удалось зарегистрировать MySQL-драйвер");
            throw new SQLException(e);
        }
    }

    /**
     * Создание соединения с базой данных MySQL на основе параметров из файла конфигурации db.properties
     *
     * @return соединение с бд
     */
    private static Connection getMySQLConnection() throws DBException {
        try (InputStream input = DBService.class.getClassLoader().getResourceAsStream(CFG_FILENAME)) {
            Properties properties = new Properties();
            properties.load(input);
            String url = properties.getProperty("url");
            return DriverManager.getConnection(url, properties);
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла конфигурации " + CFG_FILENAME);
        } catch (SQLException e) {
            System.err.println("Не удалось создать MySQL-соединение");
            throw new DBException(e);
        }
        return null;
    }

    /**
     * Вставка в таблицу нового пользователя. id в user может быть произвольным, он не учитывается
     *
     * @param user пользователь, которого вставляем в таблицу
     * @return id вставленного пользователя
     */
    public int addUser(User user) throws DBException {
        try {
            connection.setAutoCommit(false);
            UsersDao dao = new UsersDao(connection);
            dao.createTable(); // создание таблицы если она не создана
            dao.insertUser(user);
            connection.commit();
            return dao.getIdByLogin(user.getLogin());
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }

    /**
     * Удаление пользователя с указанным id
     *
     * @param id id пользователя, которого нужно удалить
     * @throws DBException
     */
    public void deleteUser(int id) throws DBException {
        try {
            connection.setAutoCommit(false);
            UsersDao dao = new UsersDao(connection);
            dao.deleteUser(id);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }

    /**
     * Изменение всех полей у пользователя с идентификатором id на данные из user.
     * id в user может быть произвольным, так как он останется прежним и не будет изменен.
     *
     * @param id   идентификатор пользователя, поля которого будут заменены
     * @param user новые поля пользователя
     * @throws DBException
     */
    public void updateUser(int id, User user) throws DBException {
        try {
            connection.setAutoCommit(false);
            UsersDao dao = new UsersDao(connection);
            dao.updateUser(id, user);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }

    /**
     * Поиск одного пользователя с указанным id и возврат
     *
     * @param id идентификатор пользователя, которого нужно найти
     * @return найденного пользователя
     * @throws DBException
     */
    public User getUser(int id) throws DBException {
        try {
            UsersDao dao = new UsersDao(connection);
            return dao.getUser(id);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    /**
     * Выборка всех пользователей из таблицы users и запись их в список
     *
     * @return список всех пользователей в таблице users
     * @throws DBException
     */
    public List<User> getAllUsers() throws DBException {
        try {
            UsersDao dao = new UsersDao(connection);
            return dao.getAllUsers();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
