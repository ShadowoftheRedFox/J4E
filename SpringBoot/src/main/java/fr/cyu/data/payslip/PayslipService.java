package fr.cyu.data.payslip;

import java.sql.Date;
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
        if (employee == null || hour <= 0 || wage <= 0 || bonus <= 0 || malus <= 0 || date == null) {
            return Optional.empty();
        }

        return Optional.of(pr.save(new Payslip(employee, hour, wage, bonus, malus, date)));
    }
}
