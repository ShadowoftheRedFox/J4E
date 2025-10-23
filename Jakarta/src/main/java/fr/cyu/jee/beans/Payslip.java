package fr.cyu.jee.beans;

import java.beans.JavaBean;
import java.sql.Date;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@JavaBean
@Entity
@Table(name = "payslip")
public class Payslip {

    @Id
    private int id;

    @NotNull
    @ManyToOne(optional = false, targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    @Column(name = "hour", nullable = false)
    private float hour;

    @Column(name = "wage", nullable = false)
    private float wage;

    @Column(name = "bonus", nullable = false)
    private float bonus;

    @Column(name = "malus", nullable = false)
    private float malus;

    @Column(name = "date", nullable = false)
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) throws NullPointerException {
        Objects.requireNonNull(user);
        this.user = user;
    }

    public float getHour() {
        return hour;
    }

    public void setHour(float hour) {
        this.hour = hour;
    }

    public float getWage() {
        return wage;
    }

    public void setWage(float wage) {
        this.wage = wage;
    }

    public float getBonus() {
        return bonus;
    }

    public void setBonus(float bonus) {
        this.bonus = bonus;
    }

    public float getMalus() {
        return malus;
    }

    public void setMalus(float malus) {
        this.malus = malus;
    }

    public Date getDate() {
        return date;
    }

    public float getTotal() {
        return wage * hour + bonus - malus;
    }

    public void setDate(Date date) throws NullPointerException {
        Objects.requireNonNull(date);
        this.date = date;
    }

    @Override
    public String toString() {
        return "Payslip [id=" + id + ", userId=" + user.getId() + ", hour=" + hour + ", wage=" + wage + ", bonus="
                + bonus + ", malus=" + malus + ", date=" + date + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Payslip) {
            return ((Payslip) obj).getId() == getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
