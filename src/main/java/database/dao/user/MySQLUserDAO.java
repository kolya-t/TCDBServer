package database.dao.user;

import database.helper.Connector;
import database.helper.executor.JdbcExecutor;
import database.pojo.User;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


/**
 * Реализация UserDAO под СУБД MySQL
 */
public class MySQLUserDAO implements UserDAO {

    /**
     * Добавляет нового пользователя в таблицу users.
     * Данные пользователя берутся из user. id в user может быть произвольным.
     * Метод изменяет id параметра user на новый.
     *
     * @param user данные пользователя, которые будут добавлены в таблицу
     * @return номер пользователя в таблице (id) или -1 если добавить не удалось
     */
    @Override
    public long insert(User user) throws SQLException {
        String sql = String.format("INSERT INTO `user` (`login`, `password`, `email`, `role`) " +
                        "VALUES ('%s', '%s', '%s', '%s')",
                user.getLogin(),
                user.getPassword(),
                user.getEmail(),
                user.getRole());

        return JdbcExecutor.executeTransaction(() -> {
            user.setId(JdbcExecutor.executeInsert(sql, Connector.getConnection()));
            return user.getId();
        });
    }

    /**
     * Операция удаления пользователя с указанным идентификатором из базы
     *
     * @param id идентификатор удаляемого пользователя
     * @return {@code true} если удаление прошло успешно и {@code false} если удалить пользователя не удалось
     */
    @Override
    public boolean delete(long id) throws SQLException {
        String sql = "DELETE FROM `user` WHERE `id` = " + id;
        return JdbcExecutor.executeTransaction(() ->
                JdbcExecutor.executeUpdate(sql, Connector.getConnection()) != 0);
    }

    /**
     * Операция обновления пользователя в базе
     *
     * @param user новый пользователь
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить пользователя не удалось
     */
    @Override
    public boolean update(User user) throws SQLException {
        String sql = String.format("UPDATE `user` " +
                        "SET `login` = '%s', `password` = '%s', `email` = '%s', `role` = '%s' WHERE `id` = %d",
                user.getLogin(), user.getPassword(), user.getEmail(), user.getRole(), user.getId());
        return JdbcExecutor.executeTransaction(() ->
                JdbcExecutor.executeUpdate(sql, Connector.getConnection()) != 0);
    }

    /**
     * Ищет в базе пользователя с указанным id и возвращает его
     *
     * @param id идентификатор пользователя, которого нужно найти
     * @return найденный пользователь или {@code null}, если пользователя найти не удалось
     */
    @Override
    public @Nullable User get(long id) throws SQLException {
        String sql = "SELECT * FROM `user` WHERE `id` = " + id;
        return JdbcExecutor.executeTransaction(() ->
                JdbcExecutor.executeQuery(sql, Connector.getConnection(), resultSet -> {
                    if (resultSet.next()) {
                        return new User(
                                resultSet.getLong("id"),
                                resultSet.getString("login"),
                                resultSet.getString("password"),
                                resultSet.getString("email"),
                                resultSet.getString("role")
                        );
                    }
                    return null;
                })
        );

    }

    /**
     * @return список всех пользователей из таблицы
     */
    @Override
    public List<User> getList() throws SQLException {
        String sql = "SELECT * FROM `user`";
        return JdbcExecutor.executeTransaction(() ->
                JdbcExecutor.executeQuery(sql, Connector.getConnection(), resultSet -> {
                    List<User> userList = new LinkedList<>();

                    while (resultSet.next()) {
                        userList.add(new User(
                                resultSet.getLong("id"),
                                resultSet.getString("login"),
                                resultSet.getString("password"),
                                resultSet.getString("email"),
                                resultSet.getString("role"))
                        );
                    }

                    return userList;
                })
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
        String sql = String.format("SELECT * FROM `user` LIMIT %d, %d", offset, limit);
        return JdbcExecutor.executeTransaction(() ->
                JdbcExecutor.executeQuery(sql, Connector.getConnection(), resultSet -> {
                    List<User> userList = new LinkedList<>();

                    while (resultSet.next()) {
                        userList.add(new User(
                                resultSet.getLong("id"),
                                resultSet.getString("login"),
                                resultSet.getString("password"),
                                resultSet.getString("email"),
                                resultSet.getString("role"))
                        );
                    }

                    return userList;
                })
        );
    }

    /**
     * @return количество записей в таблице
     */
    @Override
    public int getCount() throws SQLException {
        String sql = "SELECT count(*) FROM `user`";
        return JdbcExecutor.executeTransaction(() ->
                JdbcExecutor.executeQuery(sql, Connector.getConnection(), resultSet -> {
                    int count = 0;
                    if (resultSet.next()) {
                        count = resultSet.getInt(1);
                    }
                    return count;
                })
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
        String sql = String.format("UPDATE `user` SET `login` = '%s' WHERE `id` = %d", login, id);
        return JdbcExecutor.executeTransaction(() ->
                JdbcExecutor.executeUpdate(sql, Connector.getConnection()) != 0);
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
        String sql = String.format("UPDATE `user` SET `role` = '%s' WHERE `id` = %d", role, id);
        return JdbcExecutor.executeTransaction(() ->
                JdbcExecutor.executeUpdate(sql, Connector.getConnection()) != 0);
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
        String sql = String.format("UPDATE `user` SET `password` = '%s' WHERE `id` = %d", password, id);
        return JdbcExecutor.executeTransaction(() ->
                JdbcExecutor.executeUpdate(sql, Connector.getConnection()) != 0);
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
        String sql = String.format("UPDATE `user` SET `email` = '%s' WHERE `id` = %d", email, id);
        return JdbcExecutor.executeTransaction(() ->
                JdbcExecutor.executeUpdate(sql, Connector.getConnection()) != 0);
    }

    /**
     * Ищет в таблице пользователя с указанным login и возвращает его
     *
     * @param login логин пользователя
     * @return найденного пользователя или {@code null}, найти пользователя не удалось
     */
    @Override
    public @Nullable User getByLogin(String login) throws SQLException {
        String sql = String.format("SELECT * FROM `user` WHERE `login` = '%s'", login);
        return JdbcExecutor.executeTransaction(() ->
                JdbcExecutor.executeQuery(sql, Connector.getConnection(), resultSet -> {
                    if (resultSet.next()) {
                        System.out.println(resultSet.getLong(1));
                        System.out.println(resultSet.getString(2));
                        System.out.println(resultSet.getString(3));
                        return new User(
                                resultSet.getLong("id"),
                                resultSet.getString("login"),
                                resultSet.getString("password"),
                                resultSet.getString("email"),
                                resultSet.getString("role")
                        );
                    }
                    return null;
                })
        );
    }

    /**
     * Ищет в таблице пользователя с указанным email и возвращает его
     *
     * @param email email пользователя
     * @return найденного пользователя или {@code null}, найти пользователя не удалось
     */
    @Override
    public @Nullable User getByEmail(String email) throws SQLException {
        String sql = String.format("SELECT * FROM `user` WHERE `email` = '%s'", email);
        return JdbcExecutor.executeTransaction(() ->
                JdbcExecutor.executeQuery(sql, Connector.getConnection(), resultSet -> {
                    if (resultSet.next()) {
                        return new User(
                                resultSet.getLong("id"),
                                resultSet.getString("login"),
                                resultSet.getString("password"),
                                resultSet.getString("email"),
                                resultSet.getString("role")
                        );
                    }
                    return null;
                })
        );
    }

    /**
     * Создает таблицу если она не была создана
     */
    @Override
    public void createTableIfNotExists() throws SQLException {
        String sql =
                "CREATE TABLE IF NOT EXISTS `user` (\n" +
                        "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                        "  `login` VARCHAR(45) NOT NULL,\n" +
                        "  `password` VARCHAR(45) NOT NULL,\n" +
                        "  `email` VARCHAR(45) NOT NULL,\n" +
                        "  `role` VARCHAR(45) NOT NULL," +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE INDEX `login_UNIQUE` (`login` ASC),\n" +
                        "  UNIQUE INDEX `email_UNIQUE` (`email` ASC))\n" +
                        "ENGINE = InnoDB\n" +
                        "DEFAULT CHARACTER SET = utf8;\n";
        JdbcExecutor.executeTransaction(() ->
                JdbcExecutor.executeUpdate(sql, Connector.getConnection()));
    }

    /**
     * Полностью удаляет таблицу
     */
    @Override
    public void dropTableIfExists() throws SQLException {
        String sql = "DROP TABLE IF EXISTS `user`";
        JdbcExecutor.executeTransaction(() ->
                JdbcExecutor.executeUpdate(sql, Connector.getConnection()));
    }
}
