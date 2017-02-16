package database.dao.factory;

import database.dao.user.HibernateUserDao;
import database.dao.user.UserDAO;

/**
 * Реализации DAOFactory под Hibernate
 */
@SuppressWarnings("UnusedDeclaration")
public class HibernateDAOFactory extends DAOFactory {

    /**
     * Создает реализацию UserDAO в кдинственном экземпляре и возвращает ее
     *
     * @return созданный объект реализации UserDAO под конкретно используемую базу данных
     */
    @Override
    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new HibernateUserDao();
        }
        return userDAO;
    }
}
