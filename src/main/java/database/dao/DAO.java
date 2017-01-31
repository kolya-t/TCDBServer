package database.dao;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Интерфейс абстрактного DAO. Определены CRD операции
 *
 * @param <T> тип объекта, с котороым работает DAO
 */
@SuppressWarnings("UnusedDeclaration")
public interface DAO<T> {
    /**
     * Операция добавления объекта в БД
     *
     * @param object объект, который добавляем
     * @return номер объекта в таблице (id) или -1 если добавить не удалось
     */
    long insert(T object) throws SQLException;

    /**
     * Операция удаления объекта с указанным идентификатором из базы
     *
     * @param id идентификатор удаляемого объекта
     * @return {@code true} если удаление прошло успешно и {@code false} если удалить объект не удалось
     */
    boolean delete(long id) throws SQLException;

    /**
     * Операция удаления всех объектов из базы
     *
     * @return {@code true} если удаление прошло успешно и {@code false} если удалить объекты не удалось
     */
    boolean deleteAll() throws SQLException;

    /**
     * Ищет в базе объект с указанным id и возвращает его
     *
     * @param id идентификатор объекта, который нужно найти
     * @return найденный объект или {@code null}, если объект найти не удалось
     */
    T get(long id) throws SQLException;

    /**
     * Возвращает коллекцию всех объектов из таблицы
     *
     * @return коллекцию всех объектов из таблицы
     */
    Collection<T> getAll() throws SQLException;

    /**
     * Создает таблицу если она не была создана
     */
    void createTable() throws SQLException;

    /**
     * Полностью удаляет таблицу
     */
    void dropTable() throws SQLException;
}
