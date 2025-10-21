package org.atilla.cytraining.subjects;

import jakarta.persistence.*;

import java.util.Set;

import org.atilla.cytraining.user.User;
import org.atilla.cytraining.util.JSPUtils;

/**
 * A study subject.
 */
@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(name = "subjects_certified_users", joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> certifiedUsers;

    protected Subject() {
    }

    /**
     * Create a new subject.
     *
     * @param name           the name/title of the subject
     * @param certifiedUsers the users certified in this subject
     */
    public Subject(String name, Set<User> certifiedUsers) {
        this.name = name;
        this.certifiedUsers = certifiedUsers;
    }

    /**
     * Get the id of this subject
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name of this subject.
     */
    public String getName() {
        return JSPUtils.html2text(name);
    }

    /**
     * Set the name of this subject.
     *
     * @param name the new subject title
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the users certified in this subject.
     */
    public Set<User> getCertifiedUsers() {
        return certifiedUsers;
    }

    /**
     * Set the certified users in this subject.
     *
     * @param certifiedUsers the users certified in this subject
     */
    public void setCertifiedUsers(Set<User> certifiedUsers) {
        this.certifiedUsers = certifiedUsers;
    }
}
