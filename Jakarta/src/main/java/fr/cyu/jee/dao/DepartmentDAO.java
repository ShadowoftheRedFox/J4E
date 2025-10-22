package fr.cyu.jee.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import fr.cyu.jee.HibernateUtil;
import fr.cyu.jee.beans.Department;

public class DepartmentDAO implements DAO<Department> {
    @Override
    public Department get(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void create(Department department) {
        HibernateUtil.save(department);
    }

    @Override
    public void delete(int id) {
        HibernateUtil.remove(id, Department.class);
    }

    @Override
    public List<Department> getAll() {
        Optional<List<Department>> res = HibernateUtil.list("Department", Department.class);
        if (res.isPresent()) {
            return res.get();
        }
        return null;
    }

    @Override
    public void edit(final Department d) {
        // TODO Auto-generated method stub
    }

    @Override
    public Collection<Department> find(Map<String, Object> filter) {
        // TODO Auto-generated method stub
        return null;
    }
}
