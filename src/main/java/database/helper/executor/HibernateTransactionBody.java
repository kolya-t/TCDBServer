package database.helper.executor;

import org.hibernate.Session;

import java.sql.SQLException;


@FunctionalInterface
// можно наследовать от Function<Session, T> но не будет эксепшена
public interface HibernateTransactionBody<T> {

    T apply(Session session) throws SQLException;
}
