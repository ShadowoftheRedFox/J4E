package fr.cyu.data.project;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository pr;

    public List<Project> getAll() {
        return pr.findAll();
    }

    public Optional<Project> add(String name, Status status) {
        if (name == null || status == null || name.isBlank()) {
            return Optional.empty();
        }
        return Optional.of(pr.save(new Project(name, status)));
    }

    public Optional<Project> getById(Integer id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return pr.findById(id);
    }

    public boolean deleteById(Integer id) {
        if (id == null || id <= 0 || getById(id).isEmpty()) {
            return false;
        }

        pr.deleteById(id);
        return true;
    }

    public boolean update(Project p) {
        if (p == null || getById(p.getId()).isEmpty()) {
            return false;
        }
        pr.save(p);
        return true;
    }
}
