package fr.cyu.jee.beans;

import java.beans.JavaBean;

import fr.cyu.jee.beans.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@JavaBean
@Entity
@Table(name = "project")
public class Project {

    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Project) {
            return ((Project) obj).getId() == getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return getId() + ": " + getName() + "(" + getStatus() + ")";
    }
}