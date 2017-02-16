package database.dao.factory;

import database.dao.user.HibernateUserDao;
import database.dao.user.UserDao;

/**
 * Реализации DAOFactory под Hibernate
 */
@SuppressWarnings("UnusedDeclaration")
public class HibernateDaoFactory extends DAOFactory {

    /**
     * Создает реализацию UserDao в кдинственном экземпляре и возвращает ее
     *
     * @return созданный объект реализации UserDao под конкретно используемую базу данных
     */
    @Override
    public UserDao getUserDao() {
        if (userDao == null) {
            userDao = new HibernateUserDao();
        }
        return userDao;
    }
}
