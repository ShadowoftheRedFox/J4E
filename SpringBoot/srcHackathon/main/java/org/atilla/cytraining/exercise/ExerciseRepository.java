package org.atilla.cytraining.exercise;

// import java.nio.file.DirectoryStream.Filter;
import java.util.Optional;

import org.atilla.cytraining.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository managing exercises.
 */
@Repository
public interface ExerciseRepository<E extends Exercise> extends JpaRepository<E, Integer> {
    // E findByFilter(Filter<E> e);

    Optional<E> findById(Integer id);

    Optional<E> findByTitle(String title);

    Optional<E> findByAuthor(User author);
}