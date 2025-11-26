package fr.cyu.controller.dto;

import java.sql.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class NewPayslipDTO extends SessionDTO {
    @NotNull
    @Min(0)
    private Float hour;
    @NotNull
    @Min(0)
    private Float wage;
    @NotNull
    @Min(0)
    private Float malus;
    @NotNull
    @Min(0)
    private Float bonus;
    @NotNull
    @Min(1)
    private Integer employee;

    @NotNull
    @Min(0)
    private Long date;

    /**
     * @return the bonus
     */
    public Float getBonus() {
        return bonus;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return new Date(date);
    }

    /**
     * @return the hour
     */
    public Float getHour() {
        return hour;
    }

    /**
     * @return the malus
     */
    public Float getMalus() {
        return malus;
    }

    /**
     * @return the wage
     */
    public Float getWage() {
        return wage;
    }

    /**
     * @return the employee
     */
    public Integer getEmployee() {
        return employee;
    }

    /**
     * @param bonus the bonus to set
     */
    public void setBonus(Float bonus) {
        this.bonus = bonus;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date.getTime();
    }

    /**
     * @param hour the hour to set
     */
    public void setHour(Float hour) {
        this.hour = hour;
    }

    /**
     * @param malus the malus to set
     */
    public void setMalus(Float malus) {
        this.malus = malus;
    }

    /**
     * @param wage the wage to set
     */
    public void setWage(Float wage) {
        this.wage = wage;
    }

    /**
     * @param employee the employee to set
     */
    public void setEmployee(Integer employee) {
        this.employee = employee;
    }
}