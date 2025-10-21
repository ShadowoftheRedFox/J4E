package org.atilla.cytraining.MCQ;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MCQExerciseRepository extends JpaRepository<MCQExercise, Integer> {
    Optional<MCQExercise> findByTitle(String title);
}
