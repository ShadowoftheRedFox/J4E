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
    public boolean create(Department department) {
        return HibernateUtil.save(department);
    }

    @Override
    public boolean delete(int id) {
        return HibernateUtil.remove(id, Department.class);
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
    public boolean edit(final Department d) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Collection<Department> find(Map<String, Object> filter) {
        // TODO Auto-generated method stub
        return null;
    }
}
