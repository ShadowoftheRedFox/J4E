package fr.cyu.jee.beans;

import java.beans.JavaBean;
import java.security.Permission;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import fr.cyu.jee.beans.enums.Rank;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@JavaBean
@Entity
@Table(name = "users")
public class User {

    @Id
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    private HashSet<Rank> ranks = new HashSet<>();

    private HashSet<Permission> permissions = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws NullPointerException {
        Objects.requireNonNull(firstName);
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws NullPointerException {
        Objects.requireNonNull(lastName);
        this.lastName = lastName;
    }

    public Set<Rank> getRanks() {
        return this.ranks;
    }

    public void setRanks(Set<Rank> ranks) throws NullPointerException {
        Objects.requireNonNull(ranks);
        this.ranks = new HashSet<Rank>(ranks);
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) throws NullPointerException {
        Objects.requireNonNull(department);
        this.department = department;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof User) {
            return ((User) obj).getId() == getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return getId() + ": " + getFirstName() + " " + getLastName();
    }
}
