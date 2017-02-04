package database.dao.user;

import database.helper.HibernateSessionFactory;
import database.pojo.User;
import org.hibernate.Session;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;


public class HibernateUserDAO extends UserDAO {

    @Override
    public long insert(User user) throws SQLException {
        long id = -1;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            id = (long) session.save(user);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.flush();
            session.close();
        }
        return id;
    }

    @Override
    public boolean delete(long id) throws SQLException {
        boolean result = false;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            result = true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.flush();
            session.close();
        }
        return result;
    }

    @Override
    public boolean update(User user) {
        boolean result = false;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
            result = true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.flush();
            session.close();
        }
        return result;
    }

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

    @Override
    public List<User> getAll() throws SQLException {
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
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.flush();
            session.close();
        }
        return result;
    }

    @Override
    public boolean updateLogin(long id, String login) throws SQLException {
        return updateUserField(id, "login", login);
    }

    @Override
    public boolean updateRole(long id, String role) throws SQLException {
        return updateUserField(id, "role", role);
    }

    @Override
    public boolean updatePassword(long id, String password) throws SQLException {
        return updateUserField(id, "password", password);
    }

    @Override
    public boolean updateEmail(long id, String email) throws SQLException {
        return updateUserField(id, "email", email);
    }

    @Override
    public long getIdByLogin(String login) throws SQLException {
        String hql = "FROM User WHERE login = :login";
        long id = -1;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            User user = session.createQuery(hql, User.class)
                    .setParameter("login", login)
                    .uniqueResult();
            if (user != null) {
                id = user.getId();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        return id;
    }

    @Override
    public long getIdByEmail(String email) throws SQLException {
        String hql = "FROM User WHERE email = :email";
        long id = -1;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            User user = session.createQuery(hql, User.class)
                    .setParameter("email", email)
                    .uniqueResult();
            if (user != null) {
                id = user.getId();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        return id;
    }
}
