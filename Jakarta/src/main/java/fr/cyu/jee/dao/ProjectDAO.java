package fr.cyu.jee.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import fr.cyu.jee.beans.Department;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.SelectionQuery;

import fr.cyu.jee.HibernateUtil;
import fr.cyu.jee.HibernateUtil.SessionWrapper;
import fr.cyu.jee.beans.Project;

public class ProjectDAO implements DAO<Project> {
    @Override
    public Project get(final int id) {
        return HibernateUtil.get(Project.class, id);
    }

    @Override
    public boolean create(Project project) {
        return HibernateUtil.save(project);
    }

    @Override
    public boolean delete(final int id) {
        Boolean res = HibernateUtil.useSession(new SessionWrapper<Boolean>() {
            @Override
            public Boolean use(Transaction trs, Session session) {
                Project project = get(id);
                if (project != null) {
                    project.getUsers().clear();
                    session.remove(project);
                    return true;
                } else {
                    return false;
                }
            }
        });
        return res != null ? res : false;
    }

    @Override
    public Collection<Project> getAll() {
        Optional<List<Project>> res = HibernateUtil.list("Project", Project.class);
        if (res.isPresent()) {
            return res.get();
        }
        return null;
    }

    @Override
    public boolean edit(final Project p) {
        return HibernateUtil.update(p);
    }

    @Override
    public Collection<Project> find(Map<String, Object> filter) {
        // TODO copié coler modifié de departement y a peut être des trucs à changer pour que ça aille avec la BDD
        // https://docs.hibernate.org/orm/3.3/reference/fr-FR/html/queryhql.html
        Collection<Project> projects = HibernateUtil.useSession(new SessionWrapper<Collection<Project>>() {
            @Override
            public Collection<Project> use(Transaction trs, Session session) {
                String sql = "from Project P where 1=1 ";
                if (filter.containsKey("name")) {
                    sql += "and P.name like '*:name*' ";
                }
                sql += ";";

                SelectionQuery<Project> q = session.createSelectionQuery(sql, Project.class);

                if (filter.containsKey("name")) {
                    q.setParameter("name", filter.get("name"));
                }
                return q.getResultList();
            }
        });

        return projects;
    }
}
