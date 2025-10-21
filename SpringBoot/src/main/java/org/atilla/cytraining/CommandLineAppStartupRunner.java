package org.atilla.cytraining;

import org.atilla.cytraining.MCQ.MCQExercise;
import org.atilla.cytraining.MCQ.MCQExerciseRepository;
import org.atilla.cytraining.MCQ.entry.MCQEntry;
import org.atilla.cytraining.MCQ.entry.MCQEntryRepository;
import org.atilla.cytraining.subjects.SubjectRepository;
import org.atilla.cytraining.subjects.Subject;
import org.atilla.cytraining.user.User;
import org.atilla.cytraining.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Runner when starting the Spring app.
 */
@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private MCQExerciseRepository mcqExerciseRepository;

    @Autowired
    private MCQEntryRepository mcqEntryRepository;

    @Override
    public void run(String... args) {
        System.out.println("Populating database...");

        // 🔥 Correction : Récupérer ou créer l'admin
        User admin = userRepository.findByUsername("admin").orElseGet(() -> {
            User newAdmin = new User("admin", "admination", true, true, true, Set.of(), Set.of());
            return userRepository.save(newAdmin);
        });

        // 🔥 Correction : Récupérer ou créer le sujet
        Subject subject = subjectRepository.findByName("Développement Distribué Java EE")
                .orElseGet(() -> subjectRepository.save(new Subject("Développement Distribué Java EE", Set.of())));
        if (mcqExerciseRepository.findByTitle("Java EE et les EJB").isEmpty()) {
            // Création d'un Set d'entrées
            Set<MCQEntry> entries = new HashSet<>();

            // Création du QCM
            MCQExercise mcqExercise = new MCQExercise(
                    "Java EE et les EJB",
                    "Testez vos connaissances sur Java EE et les EJB.",
                    admin, // Assure-toi que admin est bien défini
                    false,
                    entries);

            // Création des entrées de réponse
            MCQEntry entry1 = new MCQEntry("Enterprise Java Beans", true, mcqExercise);
            MCQEntry entry2 = new MCQEntry("Extended Java Beans", false, mcqExercise);
            MCQEntry entry3 = new MCQEntry("Embedded Java Beans", false, mcqExercise);
            MCQEntry entry4 = new MCQEntry("External Java Beans", false, mcqExercise);

            // Ajout des entrées au Set
            entries.add(entry1);
            entries.add(entry2);
            entries.add(entry3);
            entries.add(entry4);

            // Sauvegarde du QCM
            mcqExercise = mcqExerciseRepository.save(mcqExercise);

            // Sauvegarde des entrées associées
            mcqEntryRepository.saveAll(entries);
            System.out.println("Entrées du QCM : ");
            for (MCQEntry entry : entries) {
                System.out.println("Question: " + entry.getAnswer() + " | Correct: " + entry.isCorrect());
            }
            System.out.println("QCM ajouté avec " + entries.size() + " questions.");
        }

        System.out.println("Application started!");
    }
}
