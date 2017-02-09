package database.dao;

import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс абстрактного DAO. Определены CRUD операции
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
    default long insert(T object) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Операция удаления объекта с указанным идентификатором из базы
     *
     * @param id идентификатор удаляемого объекта
     * @return {@code true} если удаление прошло успешно и {@code false} если удалить объект не удалось
     */
    default boolean delete(long id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Операция обновления объекта в базе
     *
     * @param object новый объект
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить объект не удалось
     */
    default boolean update(T object) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Ищет в базе объект с указанным id и возвращает его
     *
     * @param id идентификатор объекта, который нужно найти
     * @return найденный объект или {@code null}, если объект найти не удалось
     */
    default @Nullable T get(long id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * @return список всех объектов из таблицы
     */
    default List<T> getList() throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Выборка limit (или меньше) объектов из таблицы начиная с offset
     *
     * @param offset смещение от начала таблицы (считается с 0)
     * @param limit  максимальное количество объектов, которое будет выбрано
     * @return полученный список объектов или пустой список,
     * если в указанном диапазоне не найдено ни одного объекта
     */
    default List<T> getList(int offset, int limit) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * @return количество записей в таблице
     */
    default int getCount() throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Создает таблицу если она не была создана
     */
    default void createTableIfNotExists() throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Полностью удаляет таблицу
     */
    default void dropTableIfExists() throws SQLException {
        throw new UnsupportedOperationException();
    }
}
