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
    long insert(T object) throws SQLException;

    /**
     * Операция удаления объекта с указанным идентификатором из базы
     *
     * @param id идентификатор удаляемого объекта
     * @return {@code true} если удаление прошло успешно и {@code false} если удалить объект не удалось
     */
    boolean delete(long id) throws SQLException;

    /**
     * Операция обновления объекта в базе
     *
     * @param object новый объект
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить объект не удалось
     */
    boolean update(T object) throws SQLException;

    /**
     * Ищет в базе объект с указанным id и возвращает его
     *
     * @param id идентификатор объекта, который нужно найти
     * @return найденный объект или {@code null}, если объект найти не удалось
     */
    @Nullable
    T get(long id) throws SQLException;

    /**
     * @return список всех объектов из таблицы
     */
    List<T> getAll() throws SQLException;

    /**
     * Создает таблицу если она не была создана
     */
    void createTableIfNotExists() throws SQLException;

    /**
     * Полностью удаляет таблицу
     */
    void dropTableIfExists() throws SQLException;
}
