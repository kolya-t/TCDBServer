package database.dao.factory;

import database.dao.user.MySqlUserDao;
import database.dao.user.UserDao;

/**
 * Реализации DaoFactory под СУБД MySQL
 */
@SuppressWarnings("UnusedDeclaration")
public class MySqlDaoFactory extends DaoFactory {

    /**
     * Создает реализацию UserDao в кдинственном экземпляре и возвращает ее
     *
     * @return созданный объект реализации UserDao под конкретно используемую базу данных
     */
    @Override
    public UserDao getUserDao() {
        if (userDao == null) {
            userDao = new MySqlUserDao();
        }
        return userDao;
    }
}
