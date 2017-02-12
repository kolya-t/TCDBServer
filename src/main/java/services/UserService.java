package services;

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
public class UserService {

    /**
     * Экземпляр Singleton-класса {@link UserService}
     */
    private static UserService instance;

    /**
     * DAO для работы с пользователями
     */
    private final UserDAO userDAO;

    /**
     * Конструктор создает объекты реализаций всех необходимых DAO
     */
    private UserService() throws UserServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getDAOFactory(
                    PropertyService.getInstance().getDAOFactoryImplementationClassName()
            );
            userDAO = daoFactory.getUserDAO();
        } catch (Exception e) {
            throw new UserServiceException(e);
        }
    }

    public static UserService getInstance() throws UserServiceException {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    /**
     * Вставка в таблицу нового пользователя. id в user может быть произвольным, он не учитывается
     *
     * @param user пользователь, которого вставляем в таблицу
     * @return id вставленного пользователя
     */
    public long addUser(User user) throws UserServiceException {
        try {
            return userDAO.insert(user);
        } catch (SQLException e) {
            throw new UserServiceException(e);
        }
    }

    /**
     * Обновление пользователя в базе данных
     *
     * @param user новый пользователь
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить пользователя не удалось
     */
    public boolean updateUser(User user) throws UserServiceException {
        try {
            return userDAO.update(user);
        } catch (SQLException e) {
            throw new UserServiceException(e);
        }
    }

    /**
     * Удаление пользователя с указанным id
     *
     * @param id идентификатор пользователя, которого нужно удалить
     */
    public boolean deleteUser(long id) throws UserServiceException {
        try {
            return userDAO.delete(id);
        } catch (SQLException e) {
            throw new UserServiceException(e);
        }
    }

    /**
     * Заменяет login пользователя с указанным id
     *
     * @param id    идентификатор пользователя, login которого нужно заменить
     * @param login новый логин
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить login не удалось
     */
    public boolean updateLogin(long id, String login) throws UserServiceException {
        try {
            return userDAO.updateLogin(id, login);
        } catch (SQLException e) {
            throw new UserServiceException(e);
        }
    }

    /**
     * Заменяет role пользователя с указанным id
     *
     * @param id   идентификатор пользователя, role которого нужно заменить
     * @param role новая role
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить role не удалось
     */
    public boolean updateRole(long id, String role) throws UserServiceException {
        try {
            return userDAO.updateRole(id, role);
        } catch (SQLException e) {
            throw new UserServiceException(e);
        }
    }

    /**
     * Заменяет password пользователя с указанным id
     *
     * @param id       идентификатор пользователя, пароль которого нужно изменить
     * @param password новый пароль
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить пароль не удалось
     */
    public boolean updatePassword(long id, String password) throws UserServiceException {
        try {
            return userDAO.updatePassword(id, password);
        } catch (SQLException e) {
            throw new UserServiceException(e);
        }
    }

    /**
     * Заменяет email пользователя с указанным id
     *
     * @param id    идентификатор пользователя, email которого нужно изменить
     * @param email новый email
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить email не удалось
     */
    public boolean updateEmail(long id, String email) throws UserServiceException {
        try {
            return userDAO.updateEmail(id, email);
        } catch (SQLException e) {
            throw new UserServiceException(e);
        }
    }

    /**
     * Поиск одного пользователя с указанным id и возврат
     *
     * @param id идентификатор пользователя, которого нужно найти
     * @return найденного пользователя или {@code null} если пользователь с таким id не найден
     */
    public @Nullable User getUser(long id) throws UserServiceException {
        try {
            return userDAO.get(id);
        } catch (SQLException e) {
            throw new UserServiceException(e);
        }
    }

    /**
     * Выборка всех пользователей из таблицы users и запись их в список
     *
     * @return список всех пользователей в таблице users
     */
    public List<User> getUserList() throws UserServiceException {
        try {
            return userDAO.getList();
        } catch (SQLException e) {
            throw new UserServiceException(e);
        }
    }

    /**
     * Выборка limit (или меньше) пользователей из таблицы начиная с offset
     *
     * @param offset смещение от начала таблицы (считается с 0)
     * @param limit  максимальное количество пользователей, которое будет выбрано
     * @return полученный список пользователей или пустой список,
     * если в указанном диапазоне не найдено ни одного объекта
     */
    public List<User> getUserList(int offset, int limit) throws UserServiceException {
        try {
            return userDAO.getList(offset, limit);
        } catch (SQLException e) {
            throw new UserServiceException(e);
        }
    }

    /**
     * @return количество записей в таблице
     */
    public int getUserCount() throws UserServiceException {
        try {
            return userDAO.getCount();
        } catch (SQLException e) {
            throw new UserServiceException(e);
        }
    }

    /**
     * Ищет в таблице пользователя с указанным login и возвращает его
     *
     * @param login логин пользователя
     * @return найденного пользователя или {@code null}, найти пользователя не удалось
     */
    public @Nullable User getUserByLogin(String login) throws UserServiceException {
        try {
            return userDAO.getByLogin(login);
        } catch (SQLException e) {
            throw new UserServiceException(e);
        }
    }
}
