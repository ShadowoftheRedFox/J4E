package org.atilla.cytraining.user;

import jakarta.persistence.*;

import org.atilla.cytraining.exercise.Exercise;
import org.atilla.cytraining.subjects.Subject;
import org.atilla.cytraining.util.JSPUtils;

import java.util.Set;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * A user.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean admin;

    @Column(nullable = false)
    private boolean creator;

    @Column(nullable = false)
    private boolean globallyCertified;

    @ManyToMany(mappedBy = "certifiedUsers")
    private Set<Subject> certifiedSubjects;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private Set<Exercise> exercises;

    protected User() {
    }

    /**
     * Create a new user.
     *
     * @param username          the name of this user
     * @param password          the password of this user
     * @param admin             whether this user is an admin or not
     * @param creator           whether this user is a creator or not
     * @param globallyCertified whether this user is globally certified
     * @param certifiedSubjects the subjects in which this user is certified
     * @param exercises         the exercised authored by this user
     */
    public User(String username, String password, boolean admin, boolean creator, boolean globallyCertified,
            Set<Subject> certifiedSubjects, Set<Exercise> exercises) {
        this.username = username;
        this.password = encodePassword(password);
        this.admin = admin;
        this.creator = creator;
        this.globallyCertified = globallyCertified;
        this.certifiedSubjects = certifiedSubjects;
        this.exercises = exercises;
    }

    /**
     * Get the id of this user.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name of this user.
     */
    public String getUsername() {
        return JSPUtils.html2text(username);
    }

    /**
     * Set the name of this user.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the password of this user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of this user.
     *
     * @param password the new user's password
     */
    public void setPassword(String password) {
        this.password = encodePassword(password);
    }

    /**
     * Whether this user is an admin or not.
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Set whether this user is an admin or not.
     *
     * @param admin `true` to make this user an administrator, `false` otherwise
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * Whether this user is an creator or not.
     */
    public boolean isCreator() {
        return creator;
    }

    /**
     * Set whether this user is an creator or not.
     *
     * @param creator `true` to make this user an creator, `false` otherwise
     */
    public void setCreator(boolean creator) {
        this.creator = creator;
    }

    /**
     * Whether this user is certified in all subjects or not.
     */
    public boolean isGloballyCertified() {
        return globallyCertified;
    }

    /**
     * Set whether this user is globally certified or not.
     *
     * @param globallyCertified `true` to certify this user in all subjects, `false`
     *                          otherwise
     */
    public void setGloballyCertified(boolean globallyCertified) {
        this.globallyCertified = globallyCertified;

        // Si l'utilisateur est certifié globalement, il doit être creator
        if (globallyCertified) {
            this.creator = true;
        }
    }

    /**
     * Get the subjects in which this user is certified.
     * Does not return all subjects if the user is globally certified or an admin.
     */
    public Set<Subject> getCertifiedSubjects() {
        return certifiedSubjects;
    }

    /**
     * Set the subjects in which this user is certified.
     *
     * @param certifiedSubjects the new subjects in which this user is certified
     */
    public void setCertifiedSubjects(Set<Subject> certifiedSubjects) {
        this.certifiedSubjects = certifiedSubjects;
    }

    /**
     * Certify this user in a new subject.
     *
     * @param subject the new subject in which this user is certified
     */
    public void addCertifiedSubject(Subject subject) {
        certifiedSubjects.add(subject);
        subject.getCertifiedUsers().add(this);
    }

    /**
     * Remove certification for this user in the given subject.
     *
     * @param subject the subject in which the user is no longer certified
     */
    public void removeCertifiedSubject(Subject subject) {
        certifiedSubjects.remove(subject);
        subject.getCertifiedUsers().remove(this);
    }

    /**
     * Check if this user is certified in the given subject.
     *
     * @param subject the subject to check
     * @return `true` if the user is certified in this subject or globally
     *         certified/admin.
     */
    public boolean isCertifiedIn(Subject subject) {
        return admin || globallyCertified || certifiedSubjects.contains(subject);
    }

    /**
     * Get the exercises authored by this user.
     */
    public Set<Exercise> getExercises() {
        return exercises;
    }

    /**
     * Set the exercises authored by this user.
     *
     * @param exercises the exercises authored by this user
     */
    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    /**
     * Add an exercise authored by this user.
     *
     * @param exercise the new exercise authored by this user
     */
    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    /**
     * Remove an exercise from this user's stuff.
     *
     * @param exercise the exercise to be removed
     */
    public void removeExercise(Exercise exercise) {
        exercises.remove(exercise);
    }

    /**
     * Get the password and encode it as we want
     * 
     * @param password
     * @return Encoded password
     */
    private String encodePassword(String password) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // Argon2PasswordEncoder passwordEncoder =
        // Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        return passwordEncoder.encode(password);
    }

    public boolean isPassword(String password) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return passwordEncoder.matches(password, this.password);
    }

    public String getHighestRole() {
        if (this.admin) {
            return "admin";
        }
        if (this.creator) {
            return "question creator of :" + this.certifiedSubjects; // Voir comment est afficher le sujet certifier.
        }
        return "etudiant repondant au question";
    }
}