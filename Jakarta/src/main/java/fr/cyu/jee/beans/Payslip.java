package fr.cyu.jee.beans;

import java.beans.JavaBean;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@JavaBean
@Entity
@Table(name = "payslip")
public class Payslip {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "hour")
    private float hour;

    @Column(name = "wage")
    private float wage;

    @Column(name = "bonus")
    private float bonus;

    @Column(name = "malus")
    private float malus;

    @Column(name = "date")
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

    public void setUser(User user) {
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

    public void setDate(Date date) {
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
