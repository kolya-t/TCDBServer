package database;

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
@SuppressWarnings("UnusedDeclaration")
public class DBUserService {
    private static final String DB_CONFIG = "db.properties";

    private final Properties properties;
    private final Connection connection;
    private final DAOFactory daoFactory;
    private static DBUserService instance;

    public static DBUserService instance() throws DBException {
        if (instance == null) {
            instance = new DBUserService();
        }
        return instance;
    }

    private DBUserService() throws DBException {
        properties = loadProperties();
        registerDriver();
        connection = getConnection();
        daoFactory = createDAOFactory();
    }

    /**
     * Загрузка параметров из файла
     *
     * @return объект, содержащий загруженные параметры
     */
    private Properties loadProperties() throws DBException {
        try (InputStream input = DBUserService.class.getClassLoader().getResourceAsStream(DB_CONFIG)) {
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (IOException e) {
            System.err.printf("Не удалось загрузить файл с параметрами '%s'.%n", DB_CONFIG);
            throw new DBException(e);
        }
    }

    /**
     * Регистрация JDBC драйвера
     */
    private void registerDriver() throws DBException {
        String dbDriverClassName = properties.getProperty("JDBCDriverClassName", "null");
        try {
            Driver driver = (Driver) Class.forName(dbDriverClassName).newInstance();
            DriverManager.registerDriver(driver);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            System.err.printf("Не удалось зарегистрировать драйвер '%s'.%n", dbDriverClassName);
            throw new DBException(e);
        }
    }

    /**
     * Создание DAOFactory в зависимости от используемой базы данных, тип которой загружен из файла с параметрами
     *
     * @return созданный объект DAOFactory
     */
    @Nullable
    private DAOFactory createDAOFactory() throws DBException {
        String daoFactoryClassName = properties.getProperty("DAOFactoryClassName", "null");
        try {
            return DAOFactory.getDAOFactory(daoFactoryClassName, connection);
        } catch (Exception e) {
            System.err.printf("Ошибка создания DAOFactory: '%s'.%n", daoFactoryClassName);
            throw new DBException(e);
        }
    }

    /**
     * Создание соединения с базой данных на основе параметров из файла конфигурации db.properties
     *
     * @return соединение с бд
     */
    @Nullable
    private Connection getConnection() throws DBException {
        String url = properties.getProperty("ConnectionURL");
        try {
            return DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            System.err.printf("Ошибка создания соединения с базой данных '%s'.%n", url);
            throw new DBException(e);
        }
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
            UserDAO dao = daoFactory.getUserDAO();
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
     * @param id идентификатор пользователя, которого нужно удалить
     */
    public boolean deleteUser(long id) throws DBException {
        try {
            connection.setAutoCommit(false);
            UserDAO dao = daoFactory.getUserDAO();
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
     * Заменяет login пользователя с указанным id
     *
     * @param id    идентификатор пользователя, login которого нужно заменить
     * @param login новый логин
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить login не удалось
     */
    public boolean updateLogin(long id, String login) throws DBException {
        try {
            connection.setAutoCommit(false);
            UserDAO dao = daoFactory.getUserDAO();
            boolean result = dao.updateLogin(id, login);
            connection.commit();
            return result;
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
     * Заменяет name пользователя с указанным id
     *
     * @param id   идентификатор пользователя, name которого нужно заменить
     * @param name новое имя
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить name не удалось
     */
    public boolean updateName(long id, String name) throws DBException {
        try {
            connection.setAutoCommit(false);
            UserDAO dao = daoFactory.getUserDAO();
            boolean result = dao.updateName(id, name);
            connection.commit();
            return result;
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
     * Заменяет password пользователя с указанным id
     *
     * @param id       идентификатор пользователя, пароль которого нужно изменить
     * @param password новый пароль
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить пароль не удалось
     */
    public boolean updatePassword(long id, String password) throws DBException {
        try {
            connection.setAutoCommit(false);
            UserDAO dao = daoFactory.getUserDAO();
            boolean result = dao.updatePassword(id, password);
            connection.commit();
            return result;
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
     * Заменяет email пользователя с указанным id
     *
     * @param id    идентификатор пользователя, email которого нужно изменить
     * @param email новый email
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить email не удалось
     */
    public boolean updateEmail(long id, String email) throws DBException {
        try {
            connection.setAutoCommit(false);
            UserDAO dao = daoFactory.getUserDAO();
            boolean result = dao.updateEmail(id, email);
            connection.commit();
            return result;
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
     * @return найденного пользователя или {@code null} если пользователь с таким id не найден
     */
    @Nullable
    public User getUser(long id) throws DBException {
        try {
            UserDAO dao = daoFactory.getUserDAO();
            return dao.get(id);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    /**
     * Выборка всех пользователей из таблицы users и запись их в список
     *
     * @return список всех пользователей в таблице users
     */
    public List<User> getAllUsers() throws DBException {
        try {
            UserDAO dao = daoFactory.getUserDAO();
            return (List<User>) dao.getAll();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    /**
     * Закрывает соединение с базой данных и обнуляет ссылку на instance. При вызове instance()
     * после закрытия соединения конструктор будет вызван заново.
     * @return {@code true} если соединение закрыто после выполнения метода и {@code false} в ином случае
     */
    public boolean closeConnection() throws DBException {
        try {
            boolean closed = true;
            if (connection != null) {
                connection.close();
                closed = connection.isClosed();
                instance = null;
            }
            System.err.println("Connection " + (closed ? "closed" : "not closed"));
            return closed;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
