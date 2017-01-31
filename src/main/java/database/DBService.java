package database;

import database.dao.MySQLUserDAO;
import database.dao.UserDAO;
import database.dao.factory.DAOFactory;
import database.dataset.User;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Singleton класс серивиса работы с базой данных
 */
public class DBService {
    private static final String DB_CONFIG = "db.properties";

    private final Connection connection;
    private final DAOFactory daoFactory;
    private static DBService instance;

    private DBService() throws DBException {
        registerDriver();
        connection = getConnection();
        daoFactory = createDAOFactory();
    }

    public static DBService instance() throws DBException {
        if (instance == null) {
            instance = new DBService();
        }
        return instance;
    }

    /**
     * Регистрация JDBC драйвера
     */
    private static void registerDriver() throws DBException {
        try (InputStream input = DBService.class.getClassLoader().getResourceAsStream(DB_CONFIG)) {
            Properties properties = new Properties();
            properties.load(input);
            String dbDriverClassName = properties.getProperty("DBDriverClassName");

            Driver driver = (Driver) Class.forName(dbDriverClassName).newInstance();
            DriverManager.registerDriver(driver);
        } catch (SQLException | InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            System.err.println("Не удалось зарегистрировать драйвер");
            throw new DBException(e);
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла конфигурации " + DB_CONFIG);
            throw new DBException(e);
        }
    }


    @Nullable
    private DAOFactory createDAOFactory() {
        try (InputStream input = DBService.class.getClassLoader().getResourceAsStream(DB_CONFIG)) {
            Properties properties = new Properties();
            properties.load(input);
            String daoFactoryClassName = properties.getProperty("DAOFactoryClassName");

            return DAOFactory.getDAOFactory(daoFactoryClassName, connection);
        } catch (Exception e) {
            System.err.println("Ошибка при создании DAOFactory");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Создание соединения с базой данных на основе параметров из файла конфигурации db.properties
     *
     * @return соединение с бд
     */
    private static Connection getConnection() throws DBException {
        try (InputStream input = DBService.class.getClassLoader().getResourceAsStream(DB_CONFIG)) {
            Properties properties = new Properties();
            properties.load(input);
            String url = properties.getProperty("url");
            return DriverManager.getConnection(url, properties);
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла конфигурации " + DB_CONFIG);
        } catch (SQLException e) {
            System.err.println("Не удалось создать соединение сбазой данных");
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
    public long addUser(User user) throws DBException {
        try {
            connection.setAutoCommit(false);
            UserDAO dao = new MySQLUserDAO(connection);
            dao.createTable(); // создание таблицы если она не создана
            long id = dao.insert(user);
            connection.commit();
            return id;
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
    public boolean deleteUser(int id) throws DBException {
        try {
            connection.setAutoCommit(false);
            UserDAO dao = new MySQLUserDAO(connection);
            boolean isDeleted = dao.delete(id);
            connection.commit();
            return isDeleted;
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
    // TODO: заменить
    public void updateUser(int id, User user) throws DBException {
        try {
            connection.setAutoCommit(false);
            lalala dao = new lalala(connection);
            dao.updateU(id, user);
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
            lalala dao = new lalala(connection);
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
            lalala dao = new lalala(connection);
            return dao.getAllUsers();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
