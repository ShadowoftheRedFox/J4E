package fr.cyu.jee.beans;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import fr.cyu.jee.beans.enums.Permission;
import fr.cyu.jee.beans.enums.Rank;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "password")
    private String password;

    private HashSet<Rank> ranks = new HashSet<>();

    private HashSet<Permission> permissions = new HashSet<>();

//    @NotNull
//    @ManyToOne(optional = true)
//    @JoinColumn(name = "department_id")
//    private Department department;

    public User() {
        // hibernate will use this one, don't remove it
    }

    public User(String firstName, String lastName, String password) {
        setFirstName(firstName);
        setLastName(lastName);
        setPassword(password);
    }

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

    public Set<Permission> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Set<Permission> permissions) throws NullPointerException {
        Objects.requireNonNull(permissions);
        this.permissions = new HashSet<Permission>(permissions);
    }

//    public Department getDepartment() {
//        return department;
//    }
//
//    public void setDepartment(Department department) throws NullPointerException {
////        Objects.requireNonNull(department);
//        this.department = department;
//    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NullPointerException {
        Objects.requireNonNull(password);
        this.password = password;
    }

    public boolean isSame(User u) {
        if (u == null) {
            return false;
        }

        return u.firstName == firstName &&
            u.lastName == lastName &&
//            u.department.equals(department) &&
             u.permissions.equals(permissions) &&
             u.ranks.equals(ranks) &&
            u.password == password;
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
