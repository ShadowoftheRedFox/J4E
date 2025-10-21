package org.atilla.cytraining.MCQ;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.Set;

import org.atilla.cytraining.MCQ.entry.MCQEntry;
import org.atilla.cytraining.exercise.Exercise;
import org.atilla.cytraining.user.User;

/**
 * A MCQ-based exercise.
 */
@Entity
public class MCQExercise extends Exercise {

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private Set<MCQEntry> entries;

    protected MCQExercise() {
    }

    /**
     * Create a new MCQ.
     *
     * @param title       the title of the exercise
     * @param description the description of the exercise
     * @param author      the user who authored this exercise
     * @param entries     the possible answers of the MCQ including wrong ones
     */
    public MCQExercise(String title, String description, User author, boolean validated, Set<MCQEntry> entries) {
        super(title, description, author, validated);
        this.entries = entries;
    }

    /**
     * Get the entries/answers of this MCQ.
     */
    public Set<MCQEntry> getEntries() {
        return entries;
    }

    /**
     * Set the entries of this MCQ.
     *
     * @param entries the new entries/answers
     */
    public void setEntries(Set<MCQEntry> entries) {
        this.entries = entries;
    }

    /**
     * Add an entry to this MCQ.
     *
     * @param entry a possible answer (right or wrong)
     */
    public void addEntry(MCQEntry entry) {
        entries.add(entry);
    }

    /**
     * Remove an entry from this MCQ.
     *
     * @param entry the entry to remove
     */
    public void removeEntry(MCQEntry entry) {
        entries.remove(entry);
    }

    @Override
    public String toString() {
        return "MCQExercise{" +
                super.toString() +
                ", entries=" + entries +
                '}';
    }
}
