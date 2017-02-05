package database.dao.user;

import database.helper.Connector;
import database.helper.executor.SQLExecutor;
import database.pojo.User;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


/**
 * Реализация UserDAO под СУБД MySQL
 */
public class MySQLUserDAO extends UserDAO {

    /**
     * Добавляет нового пользователя в таблицу users.
     * Данные пользователя берутся из user. id в user может быть произвольным.
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
        SQLExecutor.executeUpdate(sql, Connector.getConnection());
        return getIdByLogin(user.getLogin());
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
        int result = SQLExecutor.executeUpdate(sql, Connector.getConnection());
        return result != 0;
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
        int result = SQLExecutor.executeUpdate(sql, Connector.getConnection());
        return result != 0;
    }

    /**
     * Ищет в базе пользователя с указанным id и возвращает его
     *
     * @param id идентификатор пользователя, которого нужно найти
     * @return найденный пользователь или {@code null}, если пользователя найти не удалось
     */
    @Override
    @Nullable
    public User get(long id) throws SQLException {
        String sql = "SELECT * FROM `user` WHERE `id` = " + id;
        return SQLExecutor.executeQuery(sql, Connector.getConnection(), resultSet -> {
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
                return user;
            }
            return null;
        });

    }

    /**
     * @return список всех пользователей из таблицы
     */
    @Override
    public List<User> getAll() throws SQLException {
        String sql = "SELECT * FROM `user`";
        return SQLExecutor.executeQuery(sql, Connector.getConnection(), resultSet -> {
            List<User> userList = new LinkedList<>();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
                userList.add(user);
            }

            return userList;
        });
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
        int result = SQLExecutor.executeUpdate(sql, Connector.getConnection());
        return result != 0;
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
        int result = SQLExecutor.executeUpdate(sql, Connector.getConnection());
        return result != 0;
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
        int result = SQLExecutor.executeUpdate(sql, Connector.getConnection());
        return result != 0;
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
        int result = SQLExecutor.executeUpdate(sql, Connector.getConnection());
        return result != 0;
    }

    /**
     * Ищет в таблице пользователя с указанным логином и возвращает его id
     *
     * @param login уникальный логин пользователя, которого ищем
     * @return идентификатор (id) пользователя с логином login или -1, если пользователь не найден
     */
    @Override
    public long getIdByLogin(String login) throws SQLException {
        String sql = String.format("SELECT `id` FROM `user` WHERE `login` = '%s'", login);
        long result = SQLExecutor.executeQuery(sql, Connector.getConnection(), resultSet -> {
            resultSet.next();
            long id = resultSet.getLong("id");
            resultSet.close();
            return id;
        });
        return result != 0 ? result : -1;
    }

    /**
     * Ищет в таблице пользователя с указанным email и возвращает его id
     *
     * @param email уникальный email пользователя, которого ищем
     * @return идентификатор (id) пользователя с указанным email или -1, если пользователь не найден
     */
    @Override
    public long getIdByEmail(String email) throws SQLException {
        String sql = String.format("SELECT `id` FROM `user` WHERE `email` = '%s'", email);
        long result = SQLExecutor.executeQuery(sql, Connector.getConnection(), resultSet -> {
            resultSet.next();
            long id = resultSet.getInt("id");
            resultSet.close();
            return id;
        });
        return result != 0 ? result : -1;
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
        SQLExecutor.executeUpdate(sql, Connector.getConnection());
    }

    /**
     * Полностью удаляет таблицу
     */
    @Override
    public void dropTableIfExists() throws SQLException {
        String sql = "DROP TABLE IF EXISTS `user`";
        SQLExecutor.executeUpdate(sql, Connector.getConnection());
    }
}
