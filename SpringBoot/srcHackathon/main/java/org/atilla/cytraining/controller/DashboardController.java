package org.atilla.cytraining.controller;

import java.util.Set;

import org.atilla.cytraining.exercise.ExerciceService;
import org.atilla.cytraining.subjects.Subject;
import org.atilla.cytraining.subjects.SubjectService;
import org.atilla.cytraining.user.User;
import org.atilla.cytraining.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ExerciceService exerciceService;

    private boolean isUserAdmin(HttpSession session) {
        User admin = (User) session.getAttribute("user");
        return admin != null && admin.isAdmin();
    }

    private boolean isGlobalyCertified(HttpSession session) {
        User creator = (User) session.getAttribute("user");
        return creator != null && creator.isGloballyCertified();
    }

    private boolean isCreator(HttpSession session) {
        User creator = (User) session.getAttribute("user");
        return creator != null && creator.isCreator();
    }

    @RequestMapping(value = "")
    public ModelAndView adminPage(Model model, HttpSession session) {
        if (isUserAdmin(session) || isGlobalyCertified(session) || isCreator(session)) {
            model.addAttribute("isAdmin", isUserAdmin(session));
            model.addAttribute("isGlobalyCertified", isGlobalyCertified(session));
            model.addAttribute("isCreator", isCreator(session));

            return new ModelAndView("dashboard/index");
        }

        return new ModelAndView("error");
    }

    // Affichage du tableau de bord admin avec la liste des utilisateurs
    @GetMapping("/approve")
    public ModelAndView seeApproveList(Model model, HttpSession session) {
        if (!isUserAdmin(session) || !isGlobalyCertified(session)) {
            return new ModelAndView("error");
        }

        model.addAttribute("exercises", exerciceService.findAll());

        return new ModelAndView("dashboard/approve");
    }

    @PostMapping("/approve/validate")
    public String approveExercise(@RequestParam int id, HttpSession session) {
        if (!isUserAdmin(session) | !isGlobalyCertified(session)) {
            return "error";
        }

        User user = (User) session.getAttribute("user");

        System.out.println(user.getUsername() + " is approving " + id);
        exerciceService.ValidateExercise(id);
        session.setAttribute("successMessage", "L'exercice a été approuvé avec succès.");
        return "redirect:/dashboard/approve";
    }

    @PostMapping("/approve/unvalidate")
    public String unapproveExercise(@RequestParam int id, HttpSession session) {
        if (!isUserAdmin(session) | !isGlobalyCertified(session)) {
            return "error";
        }

        User user = (User) session.getAttribute("user");

        System.out.println(user.getUsername() + " is unapproving " + id);
        exerciceService.UnvalidateExercise(id);
        session.setAttribute("successMessage", "L'exercice a été désapprouvé avec succès.");
        return "redirect:/dashboard/approve";
    }

    // Affichage du tableau de bord admin avec la liste des utilisateurs
    @GetMapping("/user-list")
    public ModelAndView adminDashboard(Model model, HttpSession session) {
        if (!isUserAdmin(session)) {
            return new ModelAndView("error");
        }

        model.addAttribute("users", userService.getAllUsers());

        return new ModelAndView("dashboard/user-list");
    }

    // Attribuer une certification globale à un utilisateur
    @PostMapping("/certify-global")
    public String certifyUserGlobally(@RequestParam int userId, HttpSession session) {
        if (!isUserAdmin(session)) {
            return "error";
        }

        System.out.println("creator  " + session.getAttribute("creator"));
        userService.certifyUserGlobally(userId);
        session.setAttribute("successMessage", "L'utilisateur a été certifié avec succès.");
        return "redirect:/dashboard/user-list";
    }

    // Retirer une certification globale d'un utilisateur
    @PostMapping("/decertify-global")
    public String removeGlobalCertification(@RequestParam int userId, HttpSession session) {
        if (!isUserAdmin(session)) {
            return "error";
        }

        userService.removeGlobalCertification(userId);
        session.setAttribute("successMessage", "L'utilisateur n'est plus certifié.");
        return "redirect:/dashboard/user-list";
    }

    // Attribuer une certification dans une matière spécifique
    @PostMapping("/certify-subject")
    public String certifyUserInSubject(@RequestParam int userId, @RequestParam int subjectId,
            @RequestParam boolean add, HttpSession session) {
        if (!isUserAdmin(session)) {
            return "error";
        }
        if (!add) {
            userService.certifyUserInSubject(userId, subjectId);
        } else {
            userService.removeSubjectCertification(userId, subjectId);
        }
        return "redirect:/dashboard/user-list";
    }

    // Va sur la liste des matieres
    @GetMapping("/subjects")
    public ModelAndView adminSubjects(Model model, HttpSession session) {
        if (!isUserAdmin(session) && !isGlobalyCertified(session) && !isCreator(session)) {
            return new ModelAndView("error");
        }
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("isAdmin", isUserAdmin(session));

        return new ModelAndView("dashboard/subjects");
    }

    // ajoute une matière
    @PostMapping("/subjects/addSubject")
    public String addSubjects(@RequestParam String name, HttpSession session) {
        if (!isUserAdmin(session) && !isGlobalyCertified(session)) {
            return "error";
        }

        Subject newSubject = new Subject(name, Set.of());
        subjectService.addSubject(newSubject);
        return "redirect:/dashboard/subjects";
    }

    // remove subjects
    @PostMapping("/subjects/removeSubject")
    public String removeSubjects(@RequestParam int id, HttpSession session) {
        if (!isUserAdmin(session)) {
            return "error";
        }
        subjectService.deleteSubject(id);
        return "redirect:/dashboard/subjects";
    }

    @GetMapping("/questions")
    public String questions(HttpSession session) {
        if (isUserAdmin(session) || isGlobalyCertified(session) || isCreator(session)) {
            return "dashboard/questions";
        }

        return "error";
    }
}