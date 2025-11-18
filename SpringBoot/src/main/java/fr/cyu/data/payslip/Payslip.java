package fr.cyu.data.payslip;

import java.sql.Date;

import fr.cyu.data.employee.Employee;
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
    @ManyToOne(optional = false, targetEntity = Employee.class, fetch = FetchType.EAGER)
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

    public Payslip() {
    }

    public Payslip(Employee employee, float hour, float wage, float bonus, float malus, Date date) {
        setEmployee(employee);
        setHour(hour);
        setWage(wage);
        setBonus(bonus);
        setMalus(malus);
        setDate(date);
    }

    /**
     * @return the employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * @param employee the employee to set
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * @return the bonus
     */
    public float getBonus() {
        return bonus;
    }

    /**
     * @param bonus the bonus to set
     */
    public void setBonus(float bonus) {
        this.bonus = bonus;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the hour
     */
    public float getHour() {
        return hour;
    }

    /**
     * @param hour the hour to set
     */
    public void setHour(float hour) {
        this.hour = hour;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the malus
     */
    public float getMalus() {
        return malus;
    }

    /**
     * @param malus the malus to set
     */
    public void setMalus(float malus) {
        this.malus = malus;
    }

    /**
     * @return the wage
     */
    public float getWage() {
        return wage;
    }

    /**
     * @param wage the wage to set
     */
    public void setWage(float wage) {
        this.wage = wage;
    }
}
