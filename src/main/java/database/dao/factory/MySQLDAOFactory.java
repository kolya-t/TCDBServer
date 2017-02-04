package database.dao.factory;

import database.dao.user.MySQLUserDAO;
import database.dao.user.UserDAO;

/**
 *  Реализации DAOFactory под СУБД MySQL
 */
@SuppressWarnings("UnusedDeclaration")
public class MySQLDAOFactory extends DAOFactory {

    /**
     * Создает реализацию UserDAO в кдинственном экземпляре и возвращает ее
     *
     * @return созданный объект реализации UserDAO под конкретно используемую базу данных
     */
    @Override
    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new MySQLUserDAO();
        }
        return userDAO;
    }
}
