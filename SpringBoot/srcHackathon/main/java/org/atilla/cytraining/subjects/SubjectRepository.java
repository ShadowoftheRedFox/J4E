package org.atilla.cytraining.subjects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository managing the subject.
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    /**
     * Find a user by its name.
     *
     * @param name the name of the target
     * @return the user corresponding to the given name or an empty Optional.
     */
    Optional<Subject> findByName(String name);
}
