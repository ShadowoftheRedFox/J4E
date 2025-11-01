package fr.cyu.data.payslip;

import java.sql.Date;

import fr.cyu.data.employee.Employee;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Payslip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(optional = false, targetEntity = Employee.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Employee employee;

    @NotNull
    @Min(0)
    private float hour;

    @NotNull
    @Min(0)
    private float wage;

    @NotNull
    @Min(0)
    private float bonus;

    @NotNull
    @Min(0)
    private float malus;

    @NotNull
    private Date date;
}
