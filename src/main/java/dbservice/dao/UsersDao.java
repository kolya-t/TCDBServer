package dbservice.dao;

import dbservice.datasets.User;
import dbservice.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Логин и email уникальны для таблицы users
 */
public class UsersDao {
    private Executor executor;

    public UsersDao(Connection connection) {
        this.executor = new Executor(connection);
    }

    /**
     * Добавляет нового пользователя в таблицу users.
     * Данные пользователя берутся из user. id в user может быть произвольным.
     *
     * @param user данные пользователя, которые будут добавлены в таблицу
     * @throws SQLException
     */
    public void insertUser(User user) throws SQLException {
        String sql = MessageFormat.format("INSERT INTO `users` (`login`, `name`, `password`, `email`) " +
                        "VALUES ('{0}', '{1}', '{2}', '{3}')",
                user.getLogin(),
                user.getName(),
                user.getPassword(),
                user.getEmail());
        executor.executeUpdate(sql);
    }

    /**
     * Изменение всех полей у пользователя с идентификатором id на данные из user.
     * id в user может быть произвольным, так как он останется прежним и не будет изменен.
     *
     * @param id   идентификатор пользователя, поля которого будут заменены
     * @param user новые поля пользователя
     */
    public void updateUser(int id, User user) throws SQLException {
        String sql = MessageFormat.format("UPDATE `users` SET " +
                        "`login`='{0}', `name`='{1}', `password`='{2}', `email`='{3}' WHERE `id`='{4}'",
                user.getLogin(),
                user.getName(),
                user.getPassword(),
                user.getEmail(),
                id);
        executor.executeUpdate(sql);
    }

    /**
     * Удаление пользователя под идентификатором id
     *
     * @param id идентификатор, пользовател с котором будет удален из таблицы
     * @throws SQLException
     */
    public void deleteUser(int id) throws SQLException {
        executor.executeUpdate("DELETE FROM `users` WHERE `id`=" + id);
    }

    /**
     * Поиск одного пользователя с указанным id и возврат
     *
     * @param id идентификатор пользователя, которого нужно найти
     * @return найденного пользователя
     * @throws SQLException
     */
    public User getUser(int id) throws SQLException {
        return executor.executeQuery("SELECT * FROM `users` WHERE `id`=" + id, resultSet -> {
            resultSet.next();
            String login = resultSet.getString("login");
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            resultSet.close();
            return new User(id, login, name, password, email);
        });
    }

    /**
     * Выборка всех пользователей из таблицы users и запись их в список
     *
     * @return список всех пользователей в таблице users
     * @throws SQLException
     */
    public List<User> getAllUsers() throws SQLException {
        return executor.executeQuery("SELECT * FROM `users`", resultSet -> {
            List<User> userList = new LinkedList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String login = resultSet.getString("login");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                userList.add(new User(id, login, name, password, email));
            }
            resultSet.close();
            return userList;
        });
    }

    /**
     * Создание таблицы users если она не создана
     *
     * @throws SQLException
     */
    public void createTable() throws SQLException {
        executor.executeUpdate("CREATE TABLE IF NOT EXISTS `users` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `login` VARCHAR(45) NOT NULL,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `password` VARCHAR(45) NOT NULL,\n" +
                "  `email` VARCHAR(45) NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE INDEX `login_UNIQUE` (`login` ASC),\n" +
                "  UNIQUE INDEX `email_UNIQUE` (`email` ASC))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;\n");
    }

    /**
     * Полное удаление таблицы users
     *
     * @throws SQLException
     */
    public void dropTable() throws SQLException {
        executor.executeUpdate("DROP TABLE `users`");
    }

    /**
     * Ищет в таблице пользователя с указанным логином и возвращает его id
     *
     * @param login уникальный логин пользователя, которого ищем
     * @return идентификатор (id) пользователя с логином login
     * @throws SQLException
     */
    public int getIdByLogin(String login) throws SQLException {
        return executor.executeQuery("SELECT * FROM `users` WHERE `login`=" + login, resultSet -> {
            resultSet.next();
            int id = resultSet.getInt("id");
            resultSet.close();
            return id;
        });
    }

    /**
     * Ищет в таблице пользователя с указанным email и возвращает его id
     *
     * @param email уникальный email пользователя, которого ищем
     * @return идентификатор (id) пользователя с указанным email
     * @throws SQLException
     */
    public int getIdByEmail(String email) throws SQLException {
        return executor.executeQuery("SELECT * FROM `users` WHERE `email`=" + email, resultSet -> {
            resultSet.next();
            int id = resultSet.getInt("id");
            resultSet.close();
            return id;
        });
    }
}
