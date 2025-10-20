package fr.cyu.jee.dao;

import java.util.Collection;
import java.util.Map;

public interface DAO<T> {
    public void create(T o);

    public void edit(int id);

    public T get(int id);

    public void delete(int id);

    public Collection<T> getAll();

    public Collection<T> find(Map<String, Object> filter);
}
