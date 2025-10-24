package fr.cyu.jee.beans;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import fr.cyu.jee.beans.enums.Permission;
import fr.cyu.jee.beans.enums.Rank;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
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

    @ElementCollection(targetClass = Rank.class)
    @JoinTable(name = "user_rank", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<String> ranks = new HashSet<>();

    @ElementCollection(targetClass = Permission.class)
    @JoinTable(name = "user_permission", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<String> permissions = new HashSet<>();

    @NotNull
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Payslip> payslips = new HashSet<>();

    @NotNull
    @ManyToOne(optional = false, targetEntity = Department.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Department department;

    public User() {
        // hibernate will use this one, don't remove it
    }

    public User(String firstName, String lastName, String password, Department department) {
        setFirstName(firstName);
        setLastName(lastName);
        setPassword(password);
        setDepartment(department);
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

    public Set<String> getRanks() {
        return this.ranks;
    }

    public void setRanks(Set<String> ranks) {
        this.ranks = ranks;
    }

    public Set<String> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Set<Payslip> getPayslips() {
        return payslips;
    }

    public void setPayslips(Set<Payslip> payslips) {
        this.payslips = payslips;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) throws NullPointerException {
        // Objects.requireNonNull(department);
        this.department = department;
    }

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
                u.department.equals(department) &&
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
