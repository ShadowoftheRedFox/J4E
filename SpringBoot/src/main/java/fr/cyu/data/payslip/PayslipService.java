package fr.cyu.data.payslip;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cyu.data.employee.Employee;

@Service
public class PayslipService {
    @Autowired
    private PayslipRepository pr;

    public List<Payslip> getAll() {
        return pr.findAll();
    }

    public Optional<Payslip> add(Employee employee, float hour, float wage, float bonus, float malus, Date date) {

        System.out.println(employee);
        System.out.println(date);
        if (employee == null || hour < 0 || wage < 0 || bonus < 0 || malus < 0 || date == null) {
            return Optional.empty();
        }

        return Optional.of(pr.save(new Payslip(employee, hour, wage, bonus, malus, date)));
    }

    public Optional<Payslip> getById(Integer id) {
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

    public List<Payslip> getAllOfEmployee(Integer id) {
        if (id == null || id <= 0) {
            return List.of();
        }

        ArrayList<Payslip> ofEmployee = new ArrayList<>();
        Iterator<Payslip> ite = getAll().iterator();
        while (ite.hasNext()) {
            Payslip p = ite.next();
            if (p.getEmployee().getId().equals(id)) {
                ofEmployee.add(p);
            }
        }

        return Collections.unmodifiableList(ofEmployee);
    }

    public boolean update(Payslip p) {
        if (p == null || getById(p.getId()).isEmpty()) {
            return false;
        }
        pr.save(p);
        return true;
    }
}
