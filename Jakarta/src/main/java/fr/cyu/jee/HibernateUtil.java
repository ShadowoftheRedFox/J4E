package fr.cyu.jee;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .configure()
                    .build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactorycreation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public interface SessionWrapper<T> {
        public T use(Transaction trs, Session session);

        default public T except(Transaction trs, Throwable e) {
            e.printStackTrace();
            if (trs != null && trs.isActive()) {
                trs.rollback();
            }
            return null;
        }
    }

    public static <T> T useSession(SessionWrapper<T> swf) {
        Transaction trs = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            trs = session.beginTransaction();
            T res = swf.use(trs, session);
            trs.commit();
            return res;
        } catch (Exception e) {
            swf.except(trs, e);
            return null;
        }
    }

    public static boolean update(Object o) {
        Boolean res = HibernateUtil.useSession(new SessionWrapper<Boolean>() {
            @Override
            public Boolean use(Transaction trs, Session session) {
                session.merge(o);
                return true;
            }
        });
        return res != null ? res : false;
    }

    public static boolean save(Object o) {
        Boolean res = HibernateUtil.useSession(new SessionWrapper<Boolean>() {
            @Override
            public Boolean use(Transaction trs, Session session) {
                session.persist(o);
                return true;
            }
        });
        return res != null ? res : false;
    }

    public static <T> boolean remove(Object id, Class<T> c) {
        Boolean res = HibernateUtil.useSession(new SessionWrapper<Boolean>() {
            @Override
            public Boolean use(Transaction trs, Session session) {
                T p = session.find(c, id);
                if (p != null) {
                    session.remove(p);
                    return true;
                } else {
                    return false;
                }
            }
        });
        return res != null ? res : false;
    }

    public static <T> Optional<List<T>> list(String name, Class<T> c) {
        Optional<List<T>> res;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            res = Optional.of(session.createQuery("from " + name, c).list());
        } catch (Exception e) {
            res = Optional.empty();
        }

        return res;
    }
}