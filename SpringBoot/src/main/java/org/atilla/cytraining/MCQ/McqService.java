package org.atilla.cytraining.MCQ;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.atilla.cytraining.MCQ.entry.MCQEntry;
import org.atilla.cytraining.MCQ.entry.MCQEntryRepository;
import org.atilla.cytraining.dto.ResponseDTO;
import org.atilla.cytraining.user.User;
import org.atilla.cytraining.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for managing MCQ questions.
 */
@Service
public class McqService {

    @Autowired
    private MCQExerciseRepository mcqRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MCQEntryRepository mcqEntryRepository;

    /**
     * Create a new MCQ question.
     *
     * @param dto the MCQ data transfer object
     * @return the created MCQ entity
     */

    public MCQExercise createMcq(String title, String description, int authorId) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

        MCQExercise mcqExercise = new MCQExercise(title, description, author, false, Set.of());
        return mcqRepository.save(mcqExercise); // Sauvegarde initiale
    }

    /**
     * Ajoute les entrées à un MCQ existant.
     */
    public void createEntries(MCQExercise mcqExercise, List<ResponseDTO> responses) {
        Set<MCQEntry> entries = responses.stream()
                .map(dto -> new MCQEntry(dto.getAnswer(), dto.isCorrect(), mcqExercise))
                .collect(Collectors.toSet());

        mcqEntryRepository.saveAll(entries); // Sauvegarde des entrées en base
        mcqExercise.setEntries(entries);
        mcqRepository.save(mcqExercise); // Mise à jour de l'exercice avec les entrées

        // afficher le mcqexercie et ses reponses
        System.out.println(
                "MCQExercise: title : " + mcqExercise.getTitle() + "  description : " + mcqExercise.getDescription());
        for (MCQEntry entry : mcqExercise.getEntries()) {
            System.out.println("MCQEntry: " + entry.getAnswer() + entry.isCorrect());
        }
    }

}
