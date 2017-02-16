package database.dao.factory;

import database.dao.user.UserDao;

/**
 * Класс содержит единственный метод, который создает экземпляр класса реализации Dao.
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class DaoFactory {

    protected UserDao userDao;

    /**
     * Создает объект класса реализации Dao, имя которого передано в качестве параметра
     *
     * @param daoFactoryClassName имя класса реализации Dao
     * @param <T>                 класс реализации Dao
     * @return новый объект типа T, созданный конструктором по-умолчанию
     */
    public static <T extends DaoFactory> T getDAOFactory(String daoFactoryClassName)
            throws Exception {
        return (T) Class.forName(daoFactoryClassName).newInstance();
    }

    /**
     * Создает реализацию UserDao в кдинственном экземпляре и возвращает ее
     *
     * @return созданный объект реализации UserDao под конкретно используемую базу данных
     */
    public abstract UserDao getUserDao();
}
