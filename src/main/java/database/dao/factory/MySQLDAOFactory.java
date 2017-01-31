package database.dao.factory;

import database.dao.MySQLUserDAO;
import database.dao.UserDAO;

import java.sql.Connection;


public class MySQLDAOFactory extends DAOFactory {

    @Override
    public UserDAO getUserDAO(Connection connection) {
        return new MySQLUserDAO(connection);
    }

//    @Override
//    public abstract AutomobileDAO getAutomobileDAO(Connection connection) {
//        return new MySQLAutomobileDAO(connection);
//    }
}
