package database.dao;

import database.executor.Executor;
import database.dataset.User;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public abstract class UserDAO implements DAO<User> {
    protected Executor executor;

    public UserDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    /**
     * Добавляет нового пользователя в таблицу users.
     * Данные пользователя берутся из user. id в user может быть произвольным.
     *
     * @param user данные пользователя, которые будут добавлены в таблицу
     * @return номер пользователя в таблице (id) или -1 если добавить не удалось
     */
    @Override
    public abstract long insert(User user) throws SQLException;

    /**
     * Операция удаления пользователя с указанным идентификатором из базы
     *
     * @param id идентификатор удаляемого пользователя
     * @return {@code true} если удаление прошло успешно и {@code false} если удалить пользователя не удалось
     */
    @Override
    public abstract boolean delete(long id) throws SQLException;

    /**
     * Операция удаления всех пользователей из базы
     *
     * @return {@code true} если удаление прошло успешно и {@code false} если удалить пользователей не удалось
     */
    @Override
    public abstract boolean deleteAll() throws SQLException;

    /**
     * Ищет в базе пользователя с указанным id и возвращает его
     *
     * @param id идентификатор пользователя, которого нужно найти
     * @return найденный пользователь или {@code null}, если пользователя найти не удалось
     */
    @Override
    @Nullable
    public abstract User get(long id) throws SQLException;

    /**
     * Возвращает коллекцию всех пользователей из таблицы
     *
     * @return коллекцию всех пользователей из таблицы
     */
    @Override
    public abstract Collection<User> getAll() throws SQLException;

    /**
     * Заменяет login пользователя с указанным id
     *
     * @param id    идентификатор пользователя, login которого нужно заменить
     * @param login новый логин
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить login не удалось
     */
    public abstract boolean updateLogin(long id, String login) throws SQLException;

    /**
     * Заменяет name пользователя с указанным id
     *
     * @param id   идентификатор пользователя, name которого нужно заменить
     * @param name новое имя
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить name не удалось
     */
    public abstract boolean updateName(long id, String name) throws SQLException;

    /**
     * Заменяет password пользователя с указанным id
     *
     * @param id       идентификатор пользователя, пароль которого нужно изменить
     * @param password новый пароль
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить пароль не удалось
     */
    public abstract boolean updatePassword(long id, String password) throws SQLException;

    /**
     * Заменяет email пользователя с указанным id
     *
     * @param id    идентификатор пользователя, email которого нужно изменить
     * @param email новый email
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить email не удалось
     */
    public abstract boolean updateEmail(long id, String email) throws SQLException;

    /**
     * Ищет в таблице пользователя с указанным логином и возвращает его id
     *
     * @param login уникальный логин пользователя, которого ищем
     * @return идентификатор (id) пользователя с логином login или -1, если пользователь не найден
     */
    public abstract long getIdByLogin(String login) throws SQLException;

    /**
     * Ищет в таблице пользователя с указанным email и возвращает его id
     *
     * @param email уникальный email пользователя, которого ищем
     * @return идентификатор (id) пользователя с указанным email или -1, если пользователь не найден
     */
    public abstract long getIdByEmail(String email) throws SQLException;

    /**
     * Создает таблицу если она не была создана
     */
    @Override
    public abstract void createTable() throws SQLException;

    /**
     * Полностью удаляет таблицу
     */
    @Override
    public abstract void dropTable() throws SQLException;
}
