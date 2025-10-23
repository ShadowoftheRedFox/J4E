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
import fr.cyu.jee.beans.Department;

public class DepartmentDAO implements DAO<Department> {
    @Override
    public Department get(int id) {
        Department department = HibernateUtil.useSession(new SessionWrapper<Department>() {
            @Override
            public Department use(Transaction trs, Session session) {
                SelectionQuery<Department> q = session.createSelectionQuery("from Department D where D.id = :id",
                        Department.class);
                q.setParameter("id", id);
                return q.getSingleResultOrNull();
            }
        });
        return department;
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
        return HibernateUtil.update(d);
    }

    @Override
    public Collection<Department> find(Map<String, Object> filter) {
        // TODO Auto-generated method stub
        return null;
    }
}
