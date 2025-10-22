package fr.cyu.jee.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.SelectionQuery;

import fr.cyu.jee.HibernateUtil;
import fr.cyu.jee.HibernateUtil.SessionWrapper;
import fr.cyu.jee.beans.UserSession;

public class UserSessionDAO implements DAO<UserSession> {

    @Override
    public void create(UserSession o) {
        HibernateUtil.save(o);
    }

    @Override
    public void delete(int id) {
        HibernateUtil.remove(id, UserSession.class);
    }

    @Override
    public Collection<UserSession> getAll() {
        Optional<List<UserSession>> sessions = HibernateUtil.list("UserSession", UserSession.class);
        if (sessions.isPresent()) {
            return sessions.get();
        }
        return null;
    }

    @Override
    public UserSession get(int id) {
        UserSession session = HibernateUtil.useSession(new SessionWrapper<UserSession>() {
            @Override
            public UserSession use(Transaction trs, Session session) {
                SelectionQuery<UserSession> q = session.createSelectionQuery("from UserSession US where US.id = :id;",
                        UserSession.class);
                q.setParameter("id", id);
                return q.getSingleResultOrNull();
            }
        });
        return session;
    }

    public UserSession getFromUserId(int user_id) {
        UserSession session = HibernateUtil.useSession(new SessionWrapper<UserSession>() {
            @Override
            public UserSession use(Transaction trs, Session session) {
                SelectionQuery<UserSession> q = session.createSelectionQuery(
                        "from UserSession US where US.user_id = :uid;",
                        UserSession.class);
                q.setParameter("uid", user_id);
                return q.getSingleResultOrNull();
            }
        });
        return session;
    }

    public UserSession getFromToken(String token) {
        UserSession session = HibernateUtil.useSession(new SessionWrapper<UserSession>() {
            @Override
            public UserSession use(Transaction trs, Session session) {
                SelectionQuery<UserSession> q = session.createSelectionQuery(
                        "from UserSession US where US.token = :token;",
                        UserSession.class);
                q.setParameter("token", token);
                return q.getSingleResultOrNull();
            }
        });
        return session;
    }

    @Override
    public Collection<UserSession> find(Map<String, Object> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void edit(UserSession value) {
        throw new UnsupportedOperationException();
    }
}
