package database;

import database.dao.factory.DAOFactory;
import database.dao.user.UserDAO;
import database.helper.PropertyService;
import database.pojo.User;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.List;

/**
 * Singleton класс сервиса работы с базой данных
 */
@SuppressWarnings("UnusedDeclaration")
public class DBService {

    /**
     * Экземпляр Singleton-класса {@link DBService}
     */
    private static DBService instance;

    /**
     * DAO для работы с пользователями
     */
    private final UserDAO userDAO;

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

    public static DBService getInstance() throws DBException {
        if (instance == null) {
            instance = new DBService();
        }
        return instance;
    }

    /**
     * Вставка в таблицу нового пользователя. id в user может быть произвольным, он не учитывается
     *
     * @param user пользователь, которого вставляем в таблицу
     * @return id вставленного пользователя
     */
    public long addUser(User user) throws DBException {
//        return executeTransaction(() -> {
//            userDAO.createTableIfNotExists();
//            return userDAO.insert(user);
//        });
        try {
            return userDAO.insert(user);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    /**
     * Обновление пользователя в базе данных
     *
     * @param user новый пользователь
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить пользователя не удалось
     */
    public boolean updateUser(User user) throws DBException {
//        return executeTransaction(() -> userDAO.update(user));
        try {
            return userDAO.update(user);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    /**
     * Удаление пользователя с указанным id
     *
     * @param id идентификатор пользователя, которого нужно удалить
     */
    public boolean deleteUser(long id) throws DBException {
//        return executeTransaction(() -> userDAO.delete(id));
        try {
            return userDAO.delete(id);
        } catch (SQLException e) {
            throw new DBException(e);
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
//        return executeTransaction(() -> userDAO.updateLogin(id, login));
        try {
            return userDAO.updateLogin(id, login);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    /**
     * Заменяет role пользователя с указанным id
     *
     * @param id   идентификатор пользователя, role которого нужно заменить
     * @param role новая role
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить role не удалось
     */
    public boolean updateRole(long id, String role) throws DBException {
//        return executeTransaction(() -> userDAO.updateRole(id, role));
        try {
            return userDAO.updateRole(id, role);
        } catch (SQLException e) {
            throw new DBException(e);
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
//        return executeTransaction(() -> userDAO.updatePassword(id, password));
        try {
            return userDAO.updatePassword(id, password);
        } catch (SQLException e) {
            throw new DBException(e);
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
//        return executeTransaction(() -> userDAO.updateEmail(id, email));
        try {
            return userDAO.updateEmail(id, email);
        } catch (SQLException e) {
            throw new DBException(e);
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
