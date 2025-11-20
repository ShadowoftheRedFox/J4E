package fr.cyu;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fr.cyu.data.department.Department;
import fr.cyu.data.department.DepartmentRepository;
import fr.cyu.data.employee.Employee;
import fr.cyu.data.employee.EmployeeRepository;
import fr.cyu.data.employee.Permission;
import fr.cyu.data.employee.Rank;

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
                .orElseGet(() -> dr.save(new Department("Administration", List.of())));

        // get admin employee, or create it
        HashSet<Rank> ranks = new HashSet<>();
        ranks.add(Rank.ADMIN);
        Employee admin = er.findByUsername("admin")
                .orElseGet(() -> er.save(new Employee("admin", "admin", "admin", "admin", administration, ranks,
                        new HashSet<Permission>())));

        if (admin.getPermissions().size() != Permission.values().length) {
            admin.setPermissions(new HashSet<Permission>(Arrays.asList(Permission.values())));
            er.save(admin);
        }

        System.out.println("Database populated, application started!");
    }
}
