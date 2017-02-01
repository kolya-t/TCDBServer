package database.dao;

/**
 * Класс содержит единственный метод, который создает экземпляр класса реализации DAO.
 */
@SuppressWarnings("UnusedDeclaration")
public final class DAOFactory {

    /**
     * Создает объект класса реализации DAO, имя которого передано в качестве параметра
     *
     * @param daoImplementationClassName имя класса реализации DAO
     * @param <T>                        класс реализации DAO
     * @return новый объект типа T, созданный конструктором по-умолчанию
     */
    public static <T extends DAO> T getDAO(String daoImplementationClassName)
            throws Exception {
        return (T) Class.forName(daoImplementationClassName).newInstance();
    }

    /**
     * Пустой приватный конструктор. Нужен, чтобызапретить пользователям
     * создавать экземпляр {@link DAOFactory}, так как в этом нет смысла.
     */
    private DAOFactory() {
    }
}
