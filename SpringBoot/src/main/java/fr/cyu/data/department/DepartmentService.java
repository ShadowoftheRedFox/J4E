package fr.cyu.data.department;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cyu.data.employee.Employee;
import fr.cyu.data.employee.EmployeeService;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository dr;

    @Autowired
    private EmployeeService es;

    public List<Department> getAll() {
        return dr.findAll();
    }

    public Optional<Department> add(final String name, final List<Employee> employees) {
        if (name == null || name.isBlank() || employees == null) {
            return Optional.empty();
        }

        Optional<Department> d = Optional.of(dr.save(new Department(name, employees)));

        if (d.isPresent()) {
            d.get().getEmployees().forEach(e -> {
                e.setDepartment(d.get());
                es.update(e);
            });
        }

        return d;
    }

    public Optional<Department> getById(final Integer id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return dr.findById(id);
    }

    public boolean update(final Department d) {
        if (d == null || getById(d.getId()).isEmpty()) {
            return false;
        }

        // update all employee
        d.getEmployees().forEach(e -> {
            e.setDepartment(d);
            es.update(e);
        });

        dr.save(d);
        return true;
    }

    public boolean deleteById(final Integer id) {
        if (id == null || id <= 0 || getById(id).isEmpty()) {
            return false;
        }

        dr.deleteById(id);
        return true;
    }
}
