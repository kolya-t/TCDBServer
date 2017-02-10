package database.dao.user;

import database.helper.HibernateSessionFactory;
import database.pojo.User;
import org.hibernate.Session;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;


/**
 * Реализация UserDAO под Hibernate
 */
public class HibernateUserDAO implements UserDAO {

    /**
     * Добавляет нового пользователя в таблицу users.
     * Данные пользователя берутся из user. id в user может быть произвольным.
     *
     * @param user данные пользователя, которые будут добавлены в таблицу
     * @return номер пользователя в таблице (id) или -1 если добавить не удалось
     */
    @Override
    public long insert(User user) throws SQLException {
        long id = -1;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            id = (long) session.save(user);
            session.flush();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return id;
    }

    /**
     * Операция удаления пользователя с указанным идентификатором из базы
     *
     * @param id идентификатор удаляемого пользователя
     * @return {@code true} если удаление прошло успешно и {@code false} если удалить пользователя не удалось
     */
    @Override
    public boolean delete(long id) throws SQLException {
        boolean result = false;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            session.flush();
            session.getTransaction().commit();
            result = true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * Операция обновления пользователя в базе
     *
     * @param user новый пользователь
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить пользователя не удалось
     */
    @Override
    public boolean update(User user) {
        boolean result = false;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(user);
            session.flush();
            session.getTransaction().commit();
            result = true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * Ищет в базе пользователя с указанным id и возвращает его
     *
     * @param id идентификатор пользователя, которого нужно найти
     * @return найденный пользователь или {@code null}, если пользователя найти не удалось
     */
    @Override
    public @Nullable User get(long id) throws SQLException {
        User user = null;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            user = session.get(User.class, id);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        return user;
    }

    /**
     * @return список всех пользователей из таблицы
     */
    @Override
    public List<User> getList() throws SQLException {
        List<User> users = new LinkedList<>();
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            users = session.createQuery("FROM User", User.class).list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        return users;
    }

    /**
     * Выборка limit (или меньше) пользователей из таблицы начиная с offset
     *
     * @param offset смещение от начала таблицы (считается с 0)
     * @param limit  максимальное количество пользователей, которое будет выбрано
     * @return полученный список пользователей или пустой список,
     * если в указанном диапазоне не найдено ни одного объекта
     */
    @Override
    public List<User> getList(int offset, int limit) throws SQLException {
        List<User> users = new LinkedList<>();
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            users = session.createQuery("FROM User", User.class)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        return users;
    }

    /**
     * @return количество записей в таблице
     */
    @Override
    public int getCount() throws SQLException {
        int count = 0;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            count = session.createQuery("SELECT COUNT(id) FROM User", Number.class)
                    .uniqueResult()
                    .intValue();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        return count;
    }

    /**
     * Обновляет любое поле пользователя
     *
     * @param id         идентификатор пользователь
     * @param fieldName  название обновляемого поля
     * @param fieldValue новое значение для поля
     * @param <T>        тип изменяемого поля
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить поле не удалось
     */
    private <T> boolean updateUserField(long id, String fieldName, T fieldValue) throws SQLException {
        String hql = MessageFormat.format("UPDATE User u SET u.{0} = :{0} WHERE u.id = :id", fieldName);
        boolean result = false;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            int updatedEntities = session
                    .createQuery(hql)
                    .setParameter("id", id)
                    .setParameter(fieldName, fieldValue)
                    .executeUpdate();
            result = updatedEntities != 0;
            session.flush();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * Заменяет login пользователя с указанным id
     *
     * @param id    идентификатор пользователя, login которого нужно заменить
     * @param login новый логин
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить login не удалось
     */
    @Override
    public boolean updateLogin(long id, String login) throws SQLException {
        return updateUserField(id, "login", login);
    }

    /**
     * Заменяет role пользователя с указанным id
     *
     * @param id   идентификатор пользователя, role которого нужно заменить
     * @param role новая role
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить role не удалось
     */
    @Override
    public boolean updateRole(long id, String role) throws SQLException {
        return updateUserField(id, "role", role);
    }

    /**
     * Заменяет password пользователя с указанным id
     *
     * @param id       идентификатор пользователя, пароль которого нужно изменить
     * @param password новый пароль
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить пароль не удалось
     */
    @Override
    public boolean updatePassword(long id, String password) throws SQLException {
        return updateUserField(id, "password", password);
    }

    /**
     * Заменяет email пользователя с указанным id
     *
     * @param id    идентификатор пользователя, email которого нужно изменить
     * @param email новый email
     * @return {@code true} если обновление прошло успешно и {@code false} если обновить email не удалось
     */
    @Override
    public boolean updateEmail(long id, String email) throws SQLException {
        return updateUserField(id, "email", email);
    }

    /**
     * Ищет в таблице пользователя с указанным login и возвращает его
     *
     * @param login логин пользователя
     * @return найденного пользователя или {@code null}, найти пользователя не удалось
     */
    @Override
    public @Nullable User getByLogin(String login) throws SQLException {
        String hql = "FROM User WHERE login = :login";
        User user = null;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            user = session.createQuery(hql, User.class)
                    .setParameter("login", login)
                    .uniqueResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        return user;
    }
}
