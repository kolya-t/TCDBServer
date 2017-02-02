package database.dao.factory;

import database.dao.DAO;
import database.dao.user.UserDAO;

/**
 * Класс содержит единственный метод, который создает экземпляр класса реализации DAO.
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class DAOFactory {

    /**
     * Создает объект класса реализации DAO, имя которого передано в качестве параметра
     *
     * @param daoFactoryClassName имя класса реализации DAO
     * @param <T>                 класс реализации DAO
     * @return новый объект типа T, созданный конструктором по-умолчанию
     */
    public static <T extends DAO> T getDAOFactory(String daoFactoryClassName)
            throws Exception {
        return (T) Class.forName(daoFactoryClassName).newInstance();
    }

    /**
     * @return созданный объект реализации UserDAO под конкретно используемую базу данных
     */
    public abstract UserDAO getUserDAO();
}
