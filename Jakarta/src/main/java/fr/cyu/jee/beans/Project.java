package fr.cyu.jee.beans;

import java.beans.JavaBean;
import java.util.Objects;
import java.util.Set;

import fr.cyu.jee.beans.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    @JoinTable(name = "user_project", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws NullPointerException {
        Objects.requireNonNull(name);
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) throws NullPointerException {
        Objects.requireNonNull(status);
        this.status = status;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) throws NullPointerException {
        Objects.requireNonNull(users);
        this.users = users;
    }

    public boolean addMembers(User user) throws NullPointerException {
        Objects.requireNonNull(user);
        return users.add(user);
    }

    public boolean removeMembers(User user) throws NullPointerException {
        Objects.requireNonNull(user);
        return users.remove(user);
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