package database.dao.user;

import database.pojo.User;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

import static database.helper.executor.HibernateExecutor.executeTransaction;


/**
 * Реализация UserDao под Hibernate
 */
public class HibernateUserDao implements UserDao {

    /**
     * Добавляет нового пользователя в таблицу users.
     * Данные пользователя берутся из user. id в user может быть произвольным.
     *
     * @param user данные пользователя, которые будут добавлены в таблицу
     * @return номер пользователя в таблице (id) или -1 если добавить не удалось
     */
    @Override
    public long insert(User user) throws SQLException {
        return executeTransaction(session ->
                (Long) session.save(user)
        );
    }

    /**
     * Операция удаления пользователя с указанным идентификатором из базы
     *
     * @param id идентификатор удаляемого пользователя
     * @return {@code true} если удаление прошло успешно и {@code false} если удалить пользователя не удалось
     */
    @Override
    public boolean delete(long id) throws SQLException {
        return executeTransaction(session -> {
            User user = session.load(User.class, id);
            session.delete(user);
            return true;
        });
    }

    /**
     * Операция обновления пользователя в базе
     *
     * @param user новый пользователь
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить пользователя не удалось
     */
    @Override
    public boolean update(User user) throws SQLException {
        return executeTransaction(session -> {
            session.update(user);
            return true;
        });
    }

    /**
     * Ищет в базе пользователя с указанным id и возвращает его
     *
     * @param id идентификатор пользователя, которого нужно найти
     * @return найденный пользователь или {@code null}, если пользователя найти не удалось
     */
    @Override
    public @Nullable User get(long id) throws SQLException {
        return executeTransaction(session ->
                session.get(User.class, id)
        );
    }

    /**
     * @return список всех пользователей из таблицы
     */
    @Override
    public List<User> getList() throws SQLException {
        return executeTransaction(session ->
                session.createQuery("FROM User", User.class).list()
        );
    }

    /**
     * Выборка limit (или меньше) пользователей из таблицы начиная с offset
     *
     * @param offset смещение от начала таблицы (считается с 0)
     * @param limit  максимальное количество пользователей, которое будет выбрано
     * @return полученный список пользователей или пустой список,
     * если в указанном диапазоне не найдено ни одного объекта
     */
    @Override
    public List<User> getList(int offset, int limit) throws SQLException {
        return executeTransaction(session ->
                session.createQuery("FROM User", User.class)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .list()
        );
    }

    /**
     * @return количество записей в таблице
     */
    @Override
    public int getCount() throws SQLException {
        return executeTransaction(session ->
                session.createQuery("SELECT COUNT(id) FROM User", Number.class)
                        .uniqueResult()
                        .intValue()
        );
    }

    @Override
    public void createTableIfNotExists() throws SQLException {
        // Hibernate automatically creates table
    }

    @Override
    public void dropTableIfExists() throws SQLException {
        // NOP
    }

    /**
     * Обновляет любое поле пользователя
     *
     * @param id         идентификатор пользователь
     * @param fieldName  название обновляемого поля
     * @param fieldValue новое значение для поля
     * @param <T>        тип изменяемого поля
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить поле не удалось
     */
    private <T> boolean updateUserField(long id, String fieldName, T fieldValue) throws SQLException {
        String hql = MessageFormat.format("UPDATE User u SET u.{0} = :{0} WHERE u.id = :id", fieldName);
        return executeTransaction(session ->
                session.createQuery(hql)
                .setParameter("id", id)
                .setParameter(fieldName, fieldValue)
                .executeUpdate() != 0
        );
    }

    /**
     * Заменяет login пользователя с указанным id
     *
     * @param id    идентификатор пользователя, login которого нужно заменить
     * @param login новый логин
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить login не удалось
     */
    @Override
    public boolean updateLogin(long id, String login) throws SQLException {
        return updateUserField(id, "login", login);
    }

    /**
     * Заменяет role пользователя с указанным id
     *
     * @param id   идентификатор пользователя, role которого нужно заменить
     * @param role новая role
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить role не удалось
     */
    @Override
    public boolean updateRole(long id, String role) throws SQLException {
        return updateUserField(id, "role", role);
    }

    /**
     * Заменяет password пользователя с указанным id
     *
     * @param id       идентификатор пользователя, пароль которого нужно изменить
     * @param password новый пароль
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить пароль не удалось
     */
    @Override
    public boolean updatePassword(long id, String password) throws SQLException {
        return updateUserField(id, "password", password);
    }

    /**
     * Заменяет email пользователя с указанным id
     *
     * @param id    идентификатор пользователя, email которого нужно изменить
     * @param email новый email
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить email не удалось
     */
    @Override
    public boolean updateEmail(long id, String email) throws SQLException {
        return updateUserField(id, "email", email);
    }

    /**
     * Ищет в таблице пользователя с указанным login и возвращает его
     *
     * @param login логин пользователя
     * @return найденного пользователя или {@code null}, найти пользователя не удалось
     */
    @Override
    public @Nullable User getByLogin(String login) throws SQLException {
        String hql = "FROM User WHERE login = :login";
        return executeTransaction(session ->
                session.createQuery(hql, User.class)
                .setParameter("login", login)
                .uniqueResult()
        );
    }

    /**
     * Ищет в таблице пользователя с указанным email и возвращает его
     *
     * @param email email пользователя
     * @return найденного пользователя или {@code null}, найти пользователя не удалось
     */
    @Override
    public User getByEmail(String email) throws SQLException {
        String hql = "FROM User WHERE email = :email";
        return executeTransaction(session ->
                session.createQuery(hql, User.class)
                .setParameter("email", email)
                .uniqueResult()
        );
    }
}
