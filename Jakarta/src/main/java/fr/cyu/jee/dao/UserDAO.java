package fr.cyu.jee.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.annotation.Nullable;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.SelectionQuery;

import fr.cyu.jee.HibernateUtil;
import fr.cyu.jee.HibernateUtil.SessionWrapper;
import fr.cyu.jee.beans.User;

public class UserDAO implements DAO<User> {
    @Override
    public User get(final int id) {
        return HibernateUtil.get(User.class, id);
    }

    @Override
    public boolean create(final User user) {
        return HibernateUtil.save(user);
    }

    @Override
    public boolean delete(final int id) {
        Boolean res = HibernateUtil.useSession(new SessionWrapper<Boolean>() {
            @Override
            public Boolean use(Transaction trs, Session session) {
                User user = get(id);
                if (user != null) {
                    session.createNativeMutationQuery("DELETE FROM user_project WHERE user_id = :userId ")
                        .setParameter("userId", id)
                        .executeUpdate();

                    session.remove(user);
                    return true;
                } else {
                    return false;
                }
            }
        });

        if (res != null) {
            return res;
        } else {
            return false;
        }
    }

    @Override
    public List<User> getAll() {
        Optional<List<User>> res = HibernateUtil.list("User", User.class);
        if (res.isPresent()) {
            return res.get();
        }
        return null;
    }

    @Override
    public boolean edit(final User u) {
        return HibernateUtil.update(u);
    }

    @Override
    public List<User> find(final Map<String, Object> filter) {
        // https://docs.hibernate.org/orm/3.3/reference/fr-FR/html/queryhql.html
        List<User> users = HibernateUtil.useSession(new SessionWrapper<List<User>>() {
            @Override
            public List<User> use(Transaction trs, Session session) {
                String sql = "from User U where 1=1 ";
                if (filter.containsKey("first_name")) {
                    sql += "and U.first_name like '*:first_name*' ";
                }
                if (filter.containsKey("last_name")) {
                    sql += "and U.last_name like '*:last_name*' ";
                }
                // TODO check if it works with sets...
                if (filter.containsKey("rank")) {
                    sql += "and U.rank = :rank ";
                }
                if (filter.containsKey("permission")) {
                    sql += "and U.role = :role ";
                }
                sql += ";";

                SelectionQuery<User> q = session.createSelectionQuery(sql, User.class);

                if (filter.containsKey("first_name")) {
                    q.setParameter("first_name", filter.get("first_name"));
                }
                if (filter.containsKey("last_name")) {
                    q.setParameter("last_name", filter.get("last_name"));
                }
                if (filter.containsKey("rank")) {
                    q.setParameter("rank", filter.get("rank"));
                }
                if (filter.containsKey("permission")) {
                    q.setParameter("role", filter.get("role"));
                }

                return q.getResultList();
            }
        });

        return users;
    }

}