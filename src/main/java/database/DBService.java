package database;

import database.dao.user.UserDAO;
import database.dao.factory.DAOFactory;
import database.pojo.User;
import database.helper.Connector;
import database.helper.PropertyService;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Singleton класс сервиса работы с базой данных
 */
@SuppressWarnings("UnusedDeclaration")
public class DBService {

    /**
     * DAO для работы с пользователями
     */
    private final UserDAO userDAO;

    /**
     * Экземпляр Singleton-класса {@link DBService}
     */
    private static DBService instance;

    public static DBService getInstance() throws DBException {
        if (instance == null) {
            instance = new DBService();
        }
        return instance;
    }

    /**
     * Конструктор создает объекты реализаций всех необходимых DAO
     */
    private DBService() throws DBException {
        try {
            DAOFactory daoFactory = DAOFactory.getDAOFactory(
                    PropertyService.getInstance().getDAOFactoryImplementationClassName()
            );
            userDAO = daoFactory.getUserDAO();
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    /**
     * Метод выполняющий транзакцию с соблюдением всех свойств транзакций.
     *
     * @param transaction транзакция
     * @param <T>         тип результата выполнения транзакции
     * @return результат выполнения команд в транзакции
     */
    private <T> T executeTransaction(Callable<T> transaction) throws DBException {
        Connection connection = Connector.getConnection();
        try {
            connection.setAutoCommit(false);
            T result = transaction.call();
            connection.commit();
            return result;
        } catch (Exception e) {
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
    }

    /**
     * Вставка в таблицу нового пользователя. id в user может быть произвольным, он не учитывается
     *
     * @param user пользователь, которого вставляем в таблицу
     * @return id вставленного пользователя
     */
    public long addUser(User user) throws DBException {
        return executeTransaction(() -> {
            userDAO.createTableIfNotExists();
            return userDAO.insert(user);
        });
    }

    /**
     * Обновление пользователя в базе данных
     *
     * @param user новый пользователь
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить пользователя не удалось
     */
    public boolean updateUser(User user) throws DBException {
        return executeTransaction(() -> userDAO.update(user));
    }

    /**
     * Удаление пользователя с указанным id
     *
     * @param id идентификатор пользователя, которого нужно удалить
     */
    public boolean deleteUser(long id) throws DBException {
        return executeTransaction(() -> userDAO.delete(id));
    }

    /**
     * Заменяет login пользователя с указанным id
     *
     * @param id    идентификатор пользователя, login которого нужно заменить
     * @param login новый логин
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить login не удалось
     */
    public boolean updateLogin(long id, String login) throws DBException {
        return executeTransaction(() -> userDAO.updateLogin(id, login));
    }

    /**
     * Заменяет name пользователя с указанным id
     *
     * @param id   идентификатор пользователя, name которого нужно заменить
     * @param name новое имя
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить name не удалось
     */
    public boolean updateName(long id, String name) throws DBException {
        return executeTransaction(() -> userDAO.updateLogin(id, name));
    }

    /**
     * Заменяет password пользователя с указанным id
     *
     * @param id       идентификатор пользователя, пароль которого нужно изменить
     * @param password новый пароль
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить пароль не удалось
     */
    public boolean updatePassword(long id, String password) throws DBException {
        return executeTransaction(() -> userDAO.updatePassword(id, password));
    }

    /**
     * Заменяет email пользователя с указанным id
     *
     * @param id    идентификатор пользователя, email которого нужно изменить
     * @param email новый email
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить email не удалось
     */
    public boolean updateEmail(long id, String email) throws DBException {
        return executeTransaction(() -> userDAO.updateEmail(id, email));
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
            return userDAO.get(id);
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
            return userDAO.getAll();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
