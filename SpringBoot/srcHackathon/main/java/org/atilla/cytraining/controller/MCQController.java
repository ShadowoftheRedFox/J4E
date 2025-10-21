package org.atilla.cytraining.controller;

import org.atilla.cytraining.MCQ.MCQExercise;
import org.atilla.cytraining.MCQ.MCQExerciseRepository;
import org.atilla.cytraining.MCQ.McqDTO;
import org.atilla.cytraining.MCQ.McqService;
import org.atilla.cytraining.MCQ.entry.MCQEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/mcq")
public class MCQController {

    private boolean isUserLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    @Autowired
    private MCQExerciseRepository mcqExerciseRepository;

    @Autowired
    private McqService mcqService;

    @GetMapping("/{id}")
    public String showMCQ(@PathVariable int id, Model model, HttpSession session) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/login"; // Redirection vers la page de connexion
        }

        Optional<MCQExercise> mcqOpt = mcqExerciseRepository.findById(id);

        if (mcqOpt.isEmpty()) {
            model.addAttribute("error", "QCM introuvable !");
            return "error";
        }

        MCQExercise mcq = mcqOpt.get();
        Set<MCQEntry> entries = mcq.getEntries();
        System.out.println(mcq.getEntries());

        // DEBUG : Afficher le nombre de questions et leurs contenus dans la console
        System.out.println("QCM: " + mcq.getTitle() + " - Nombre de questions: " + entries.size());
        for (MCQEntry entry : entries) {
            System.out.println("Question: " + entry.getAnswer() + " | Correct: " + entry.isCorrect());
        }

        model.addAttribute("mcq", mcq);
        model.addAttribute("entries", entries);
        return "mcq";
    }

    @PostMapping("/{id}/submit")
    public String submitAnswer(@PathVariable int id, @RequestParam("answerId") int answerId, Model model) {
        Optional<MCQExercise> mcqOpt = mcqExerciseRepository.findById(id);

        if (mcqOpt.isEmpty()) {
            model.addAttribute("error", "QCM introuvable !");
            return "error";
        }

        MCQExercise mcq = mcqOpt.get();
        Set<MCQEntry> entries = mcq.getEntries();
        MCQEntry selectedEntry = entries.stream().filter(e -> e.getId() == answerId).findFirst().orElse(null);

        if (selectedEntry == null) {
            model.addAttribute("error", "Réponse invalide !");
            return "error";
        }

        boolean isCorrect = selectedEntry.isCorrect();
        model.addAttribute("mcq", mcq);
        model.addAttribute("entries", entries);
        model.addAttribute("selectedEntry", selectedEntry);
        model.addAttribute("isCorrect", isCorrect);

        return "mcq";
    }

    @PostMapping("/add")
    public ModelAndView addMcq(@Valid @ModelAttribute("mcq") McqDTO dto, BindingResult bindingResult,
            @RequestParam int userId) {

        System.out.println(dto.getResponses());

        if (bindingResult.hasErrors() || dto.getResponses() == null || dto.getResponses().isEmpty()) {
            ModelAndView mav = new ModelAndView("dashboard/questions");
            mav.addObject("error_responses", "Veuillez ajouter au moins une réponse.");
            return mav;
        }

        // 1. Création de l'exercice sans entrées
        MCQExercise mcq = mcqService.createMcq(dto.getTitle(), dto.getDescription(), userId);

        // 2. Ajout des entrées après avoir obtenu un ID
        mcqService.createEntries(mcq, dto.getResponses());

        return new ModelAndView("redirect:/dashboard/questions");
    }
}
