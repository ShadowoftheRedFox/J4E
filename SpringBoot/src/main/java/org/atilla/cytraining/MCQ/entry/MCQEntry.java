package org.atilla.cytraining.MCQ.entry;

import java.util.Objects;

import org.atilla.cytraining.MCQ.MCQExercise;

import jakarta.persistence.*;

/**
 * A MCQ entry.
 */
@Entity
@Table(name = "mcq_entries")
public class MCQEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private boolean correct;

    @ManyToOne(optional = false)
    private MCQExercise parent;

    protected MCQEntry() {
    }

    /**
     * Create a new entry for a MCQ.
     *
     * @param answer  the label of this possible answer
     * @param correct true if this answer is correct or not
     * @param parent  the exercise containing this entry
     */
    public MCQEntry(String answer, boolean correct, MCQExercise parent) {
        this.answer = answer;
        this.correct = correct;
        this.parent = parent;
    }

    /**
     * Get the id of this entry.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the label of this entry.
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Set the answer of this entry.
     *
     * @param answer the label of this answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Whether this answer is a correct one or not.
     */
    public boolean isCorrect() {
        return correct;
    }

    /**
     * Set this answer as correct or not.
     *
     * @param correct whether this entry is correct or not
     */
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    /**
     * Get the parent of this entry.
     */
    public MCQExercise getParent() {
        return parent;
    }

    /**
     * Set the parent of this entry.
     *
     * @param parent the new owner of this entry
     */
    public void setParent(MCQExercise parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MCQEntry mcqEntry = (MCQEntry) o;
        return correct == mcqEntry.correct &&
                Objects.equals(answer, mcqEntry.answer) &&
                Objects.equals(parent, mcqEntry.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answer, correct, parent);
    }
}