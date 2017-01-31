package database.dao.factory;

import database.dao.UserDAO;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.sql.Connection;


@SuppressWarnings("UnusedDeclaration")
public abstract class DAOFactory {
    @Nullable
    public static <T extends DAOFactory> T getDAOFactory(String daoImplementationClassName, Connection connection)
            throws Exception {

        Class clazz = Class.forName(daoImplementationClassName);
        Constructor constructor = clazz.getConstructor(Connection.class);

        return (T) constructor.newInstance(connection);
    }

    public abstract UserDAO getUserDAO(Connection connection);

//    public abstract AutomobileDAO getAutomobileDAO(Connection connection);
}
