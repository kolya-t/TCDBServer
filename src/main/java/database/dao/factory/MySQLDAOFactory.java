package database.dao.factory;

import database.dao.user.MySQLUserDAO;
import database.dao.user.UserDAO;

/**
 *  Реализации DAOFactory по СУБД MySQL
 */
@SuppressWarnings("UnusedDeclaration")
public class MySQLDAOFactory extends DAOFactory {

    /**
     * @return созданный объект реализации UserDAO под конкретно используемую базу данных
     */
    @Override
    public UserDAO getUserDAO() {
        return new MySQLUserDAO();
    }
}
