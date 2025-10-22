package fr.cyu.jee.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import fr.cyu.jee.HibernateUtil;
import fr.cyu.jee.beans.Project;

public class ProjectDAO implements DAO<Project> {
    @Override
    public Project get(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void create(Project project) {
        HibernateUtil.save(project);
    }

    @Override
    public void delete(int id) {
        HibernateUtil.remove(id, Project.class);
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
    public void edit(final Project p) {
        // TODO Auto-generated method stub
    }

    @Override
    public Collection<Project> find(Map<String, Object> filter) {
        // TODO Auto-generated method stub
        return null;
    }
}
