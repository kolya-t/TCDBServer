package database.dao.user;

import database.dao.DAO;
import database.pojo.User;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.List;


@SuppressWarnings("UnusedDeclaration")
public interface UserDAO extends DAO<User> {

    /**
     * Добавляет нового пользователя в таблицу users.
     * Данные пользователя берутся из user. id в user может быть произвольным.
     *
     * @param user данные пользователя, которые будут добавлены в таблицу
     * @return номер пользователя в таблице (id) или -1 если добавить не удалось
     */
    @Override
    long insert(User user) throws SQLException;

    /**
     * Операция удаления пользователя с указанным идентификатором из базы
     *
     * @param id идентификатор удаляемого пользователя
     * @return {@code true} если удаление прошло успешно и {@code false} если удалить пользователя не удалось
     */
    @Override
    boolean delete(long id) throws SQLException;

    /**
     * Операция обновления пользователя в базе
     *
     * @param user новый пользователь
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить пользователя не удалось
     */
    @Override
    boolean update(User user) throws SQLException;

    /**
     * Ищет в базе пользователя с указанным id и возвращает его
     *
     * @param id идентификатор пользователя, которого нужно найти
     * @return найденный пользователь или {@code null}, если пользователя найти не удалось
     */
    @Override
    @Nullable User get(long id) throws SQLException;

    /**
     * @return список всех пользователей из таблицы
     */
    @Override
    List<User> getList() throws SQLException;

    /**
     * Выборка limit (или меньше) пользователей из таблицы начиная с offset
     *
     * @param offset смещение от начала таблицы (считается с 0)
     * @param limit  максимальное количество пользователей, которое будет выбрано
     * @return полученный список пользователей или пустой список,
     * если в указанном диапазоне не найдено ни одного объекта
     */
    @Override
    List<User> getList(int offset, int limit) throws SQLException;

    /**
     * @return количество записей в таблице
     */
    @Override
    int getCount() throws SQLException;

    /**
     * Заменяет login пользователя с указанным id
     *
     * @param id    идентификатор пользователя, login которого нужно заменить
     * @param login новый логин
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить login не удалось
     */
    boolean updateLogin(long id, String login) throws SQLException;

    /**
     * Заменяет role пользователя с указанным id
     *
     * @param id   идентификатор пользователя, role которого нужно заменить
     * @param role новая role
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить role не удалось
     */
    boolean updateRole(long id, String role) throws SQLException;

    /**
     * Заменяет password пользователя с указанным id
     *
     * @param id       идентификатор пользователя, пароль которого нужно изменить
     * @param password новый пароль
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить пароль не удалось
     */
    boolean updatePassword(long id, String password) throws SQLException;

    /**
     * Заменяет email пользователя с указанным id
     *
     * @param id    идентификатор пользователя, email которого нужно изменить
     * @param email новый email
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить email не удалось
     */
    boolean updateEmail(long id, String email) throws SQLException;

    /**
     * Ищет в таблице пользователя с указанным login и возвращает его
     *
     * @param login логин пользователя
     * @return найденного пользователя или {@code null}, найти пользователя не удалось
     */
    @Nullable User getByLogin(String login) throws SQLException;
}
