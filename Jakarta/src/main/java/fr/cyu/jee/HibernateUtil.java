package fr.cyu.jee;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import fr.cyu.jee.beans.Department;
import fr.cyu.jee.beans.Project;
import fr.cyu.jee.beans.User;

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

    public interface SessionWrapper {
        public void use(Transaction trs, Session session);

        default public void except(Transaction trs, Throwable e) {
            trs.rollback();
            e.printStackTrace();
        }
    }

    public static boolean useSession(SessionWrapper swf) {
        Transaction trs = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            trs = session.beginTransaction();
            swf.use(trs, session);
            trs.commit();
            return true;
        } catch (Exception e) {
            swf.except(trs, e);
            return false;
        }
    }

    public static boolean persist(Object o) {
        return HibernateUtil.useSession(new SessionWrapper() {
            @Override
            public void use(Transaction trs, Session session) {
                session.persist(o);
            }
        });
    }

    public static <T> boolean remove(Object id, Class<T> c) {
        return HibernateUtil.useSession(new SessionWrapper() {
            @Override
            public void use(Transaction trs, Session session) {
                T p = session.get(c, id);
                if (p != null) {
                    session.remove(p);
                }
            }
        });
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