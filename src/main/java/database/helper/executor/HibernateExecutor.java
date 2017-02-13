package database.helper.executor;

import database.helper.HibernateSessionFactory;
import org.hibernate.Session;

import java.sql.SQLException;

public final class HibernateExecutor {

    /**
     * Метод, выполняющий Hibernate странзакцию и её фиксацию. В случае неудачи выполняется откат.
     * После выполнения транзакции сессия закрывается в методе
     *
     * @param transactionBody тело странзакции
     * @param <T>             тип значения, возвращаемого транзакцией
     * @return результат транзакции
     */
    public static <T> T executeTransaction(HibernateTransactionBody<T> transactionBody) throws SQLException {
        T value;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            value = transactionBody.apply(session);
            session.flush();
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            throw new SQLException(e);
        } finally {
            session.close();
        }
        return value;
    }
}
