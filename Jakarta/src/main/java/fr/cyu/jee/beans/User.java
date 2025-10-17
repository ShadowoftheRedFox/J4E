package fr.cyu.jee.beans;

import java.beans.JavaBean;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import fr.cyu.jee.beans.enums.Permission;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@JavaBean
@Entity
@Table(name = "user")
public class User {

    @Id
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private HashSet<Permission> permissions = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Permission> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Collection<Permission> permissions) {
        this.permissions = new HashSet<Permission>(permissions);
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
