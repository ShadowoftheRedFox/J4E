package org.atilla.cytraining.MCQ.entry;

import org.atilla.cytraining.MCQ.MCQExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MCQEntryRepository extends JpaRepository<MCQEntry, Integer> {
    List<MCQEntry> findByParent(MCQExercise parent); // Récupérer les entrées d'un MCQ
}
