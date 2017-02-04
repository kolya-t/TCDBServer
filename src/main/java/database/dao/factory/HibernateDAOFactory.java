package database.dao.factory;

import database.dao.user.HibernateUserDAO;
import database.dao.user.UserDAO;

public class HibernateDAOFactory extends DAOFactory {
    @Override
    public UserDAO getUserDAO() {
        return new HibernateUserDAO();
    }
}
