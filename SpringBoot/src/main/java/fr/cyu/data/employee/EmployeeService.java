package fr.cyu.data.employee;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cyu.data.department.Department;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository er;

    public List<Employee> getAll() {
        return er.findAll();
    }

    public Optional<Employee> add(String username, String password, String first, String last, Department d) {
        if (username == null || password == null || first == null || first == null || last == null || username.isBlank()
                || password.isBlank() || first.isBlank() || first.isBlank() || last.isBlank() || d == null) {
            return Optional.empty();
        }

        return Optional.of(er.save(new Employee(username, password, first, last, d)));
    }

    public Optional<Employee> getById(Integer id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return er.findById(id);
    }

    public boolean deleteById(Integer id) {
        if (id == null || id <= 0) {
            return false;
        }
        if (getById(id).isEmpty()) {
            return false;
        }

        er.deleteById(id);
        return true;
    }

    public boolean update(Employee e) {
        if (e == null || getById(e.getId()).isEmpty()) {
            return false;
        }
        er.save(e);
        return true;
    }
}
