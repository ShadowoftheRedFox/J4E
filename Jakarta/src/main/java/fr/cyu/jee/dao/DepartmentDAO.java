package fr.cyu.jee.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import fr.cyu.jee.beans.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.SelectionQuery;

import fr.cyu.jee.HibernateUtil;
import fr.cyu.jee.HibernateUtil.SessionWrapper;
import fr.cyu.jee.beans.Department;

public class DepartmentDAO implements DAO<Department> {
    @Override
    public Department get(int id) {
       return HibernateUtil.get(Department.class, id);
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
        // TODO j'arrive pas à lancer le projet (rip eclipse) donc pas testé
        // https://docs.hibernate.org/orm/3.3/reference/fr-FR/html/queryhql.html
        Collection<Department> departments = HibernateUtil.useSession(new SessionWrapper<Collection<Department>>() {
            @Override
            public Collection<Department> use(Transaction trs, Session session) {
                String sql = "from Department D where 1=1 ";
                if (filter.containsKey("name")) {
                    sql += "and D.name like '*:name*' ";
                }
                sql += ";";

                SelectionQuery<Department> q = session.createSelectionQuery(sql, Department.class);

                if (filter.containsKey("name")) {
                    q.setParameter("name", filter.get("name"));
                }
                return q.getResultList();
            }
        });

        return departments;
    }
}
