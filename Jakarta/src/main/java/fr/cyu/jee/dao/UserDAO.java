package fr.cyu.jee.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import fr.cyu.jee.beans.User;
import fr.cyu.jee.HibernateUtil;
import fr.cyu.jee.HibernateUtil.SessionWrapper;

public class UserDAO implements DAO<User> {
    @Override
    public User get(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void create(User user) {
        HibernateUtil.persist(user);
    }

    @Override
    public void delete(int id) {
        HibernateUtil.remove(id, User.class);
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
    public void edit(int id) {
        // TODO Auto-generated method stub
    }

    @Override
    public Collection<User> find(Map<String, Object> filter) {
        // TODO Auto-generated method stub
        return null;
    }

}