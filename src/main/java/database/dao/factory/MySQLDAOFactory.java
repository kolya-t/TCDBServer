package database.dao.factory;

import database.dao.MySQLUserDAO;
import database.dao.UserDAO;

import java.sql.Connection;


public class MySQLDAOFactory extends DAOFactory {

    private Connection connection;

    public MySQLDAOFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UserDAO getUserDAO() {
        return new MySQLUserDAO(connection);
    }

//    @Override
//    public abstract AutomobileDAO getAutomobileDAO(Connection connection) {
//        return new MySQLAutomobileDAO(connection);
//    }
}
