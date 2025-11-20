package fr.cyu.data.department;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository dr;

    public List<Department> getAll() {
        return dr.findAll();
    }

    public Optional<Department> add(String name) {
        if (name == null || name.isBlank()) {
            return Optional.empty();
        }
        return Optional.of(dr.save(new Department(name)));
    }

    public Optional<Department> getById(Integer id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return dr.findById(id);
    }

    public boolean update(Department d) {
        if (d == null || getById(d.getId()).isEmpty()) {
            return false;
        }
        dr.save(d);
        return true;
    }
}
