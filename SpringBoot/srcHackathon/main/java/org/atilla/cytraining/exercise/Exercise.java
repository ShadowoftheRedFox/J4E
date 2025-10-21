package org.atilla.cytraining.exercise;

import org.atilla.cytraining.user.User;
import org.atilla.cytraining.util.JSPUtils;

import jakarta.persistence.*;

/**
 * An exercise.
 */
@Entity
@Table(name = "exercises")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne(optional = false)
    private User author;

    @Column(nullable = false)
    private boolean validated;

    protected Exercise() {
    }

    /**
     * Create a new exercise.
     *
     * @param title       the title of the exercise
     * @param description the description of the exercise
     * @param author      the user who authored this exercise
     */
    public Exercise(String title, String description, User author, boolean validated) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.validated = validated;
    }

    /**
     * Get the id of this exercise.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the title of this exercise.
     */
    public String getTitle() {
        return JSPUtils.html2text(title);
    }

    /**
     * Get the description of this exercise.
     */
    public String getDescription() {
        return JSPUtils.html2text(description);
    }

    /**
     * Set the title of this exercise.
     *
     * @param title the new title of this exercise
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the author of this exercise.
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Set the author of this exercise.
     *
     * @param author the new author of this exercise
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    public void setValidated(boolean bool) {
        this.validated = bool;
    }

    public boolean getValidated() {
        return this.validated;
    }
}