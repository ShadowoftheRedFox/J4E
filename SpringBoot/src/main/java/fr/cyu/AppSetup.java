package fr.cyu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fr.cyu.data.department.Department;
import fr.cyu.data.department.DepartmentRepository;
import fr.cyu.data.employee.Employee;
import fr.cyu.data.employee.EmployeeRepository;

@Component
public class AppSetup implements CommandLineRunner {
    @Autowired
    private DepartmentRepository dr;

    @Autowired
    private EmployeeRepository er;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Populating database...");

        // get administration derpatment, or create it
        Department administration = dr.findByName("Administration")
                .orElseGet(() -> dr.save(new Department("Administration")));

        // get admin employee, or create it
        Employee admin = er.findByUsername("admin")
                .orElseGet(() -> er.save(new Employee("admin", "admin", "admin", "admin", administration)));

        System.out.println("Database populated, application started!");
    }
}
