package database.dao;

import database.dataset.User;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class MySQLUserDAO extends UserDAO {

    public MySQLUserDAO(Connection connection) {
        super(connection);
    }

    /**
     * Добавляет нового пользователя в таблицу users.
     * Данные пользователя берутся из user. id в user может быть произвольным.
     *
     * @param user данные пользователя, которые будут добавлены в таблицу
     * @return номер пользователя в таблице (id) или -1 если добавить не удалось
     */
    @Override
    public long insert(User user) throws SQLException {
        String sql = String.format("INSERT INTO `user` (`login`, `name`, `password`, `email`) " +
                        "VALUES ('%s', '%s', '%s', '%s')",
                user.getLogin(),
                user.getName(),
                user.getPassword(),
                user.getEmail());
        executor.executeUpdate(sql);
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
        int result = executor.executeUpdate(sql);
        return result != 0;
    }

    /**
     * Операция удаления всех пользователей из базы
     *
     * @return {@code true} если удаление прошло успешно и {@code false} если удалить пользователей не удалось
     */
    @Override
    public boolean deleteAll() throws SQLException {
        String sql = "DELETE FROM `user`";
        int result = executor.executeUpdate(sql);
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
        return executor.executeQuery(sql, resultSet -> {
            if (resultSet.next()) {
                return new User(
                        resultSet.getLong("id"),
                        resultSet.getString("login"),
                        resultSet.getString("name"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                );
            }
            return null;
        });

    }

    /**
     * Возвращает список всех пользователей из таблицы
     *
     * @return список всех пользователей из таблицы или пустой список, если таблица пуста
     */
    @Override
    public List<User> getAll() throws SQLException {
        String sql = "SELECT * FROM `user`";
        return executor.executeQuery(sql, resultSet -> {
            List<User> userList = new LinkedList<>();

            while (resultSet.next()) {
                userList.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("login"),
                        resultSet.getString("name"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                ));
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
        int result = executor.executeUpdate(sql);
        return result != 0;
    }

    /**
     * Заменяет name пользователя с указанным id
     *
     * @param id   идентификатор пользователя, name которого нужно заменить
     * @param name новое имя
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить name не удалось
     */
    @Override
    public boolean updateName(long id, String name) throws SQLException {
        String sql = String.format("UPDATE `user` SET `name` = '%s' WHERE `id` = %d", name, id);
        int result = executor.executeUpdate(sql);
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
        int result = executor.executeUpdate(sql);
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
        int result = executor.executeUpdate(sql);
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
        long result = executor.executeQuery(sql, resultSet -> {
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
        long result = executor.executeQuery(sql, resultSet -> {
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
    public void createTable() throws SQLException {
        String sql =
                "CREATE TABLE IF NOT EXISTS `user` (\n" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  `login` VARCHAR(45) NOT NULL,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `password` VARCHAR(45) NOT NULL,\n" +
                "  `email` VARCHAR(45) NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `login_UNIQUE` (`login` ASC),\n" +
                "  UNIQUE INDEX `email_UNIQUE` (`email` ASC))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;\n";
        executor.executeUpdate(sql);
    }

    /**
     * Полностью удаляет таблицу
     */
    @Override
    public void dropTable() throws SQLException {
        String sql = "DROP TABLE `user`";
        executor.executeUpdate(sql);
    }
}
