package org.atilla.cytraining.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.atilla.cytraining.dto.LoginDTO;
import org.atilla.cytraining.dto.RegisterDTO;
import org.atilla.cytraining.service.AuthService;
import org.atilla.cytraining.subjects.SubjectService;
import org.atilla.cytraining.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.atilla.cytraining.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for authentication-related endpoints.
 */
@Controller
public class AuthController implements WebMvcConfigurer {

    @Autowired
    private AuthService authService;

    @Autowired
    private SubjectService subjectService;

    /**
     * Login page.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    /**
     * Login auth endpoint.
     */
    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ModelAndView login(@Valid LoginDTO dto, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors())
            return ValidationUtils.errorsAndView("login", bindingResult);
        Optional<User> user = authService.login(dto.getUsername(), dto.getPassword());
        if (user.isPresent()) {
            session.setAttribute("user", user.get());
            session.setAttribute("subjects", subjectService.getAllSubjects());
            return new ModelAndView("redirect:/");
        } else {
            return new ModelAndView("login", Map.of("error_login", "Identifiants incorrects"));
        }
    }

    /**
     * Register auth endpoint.
     */
    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public ModelAndView register(@Valid RegisterDTO dto, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors())
            return ValidationUtils.errorsAndView("login", bindingResult);
        Map<String, String> errors = new HashMap<>();
        if (!dto.isPasswordConfirmed())
            errors.put("error_password", "les deux mots de passe ne correspondent pas");
        if (errors.isEmpty()) {
            User user = authService.register(dto.getUsername(), dto.getPassword());
            if (user == null) {
                errors.put("error_password", "Identifiants incorrects");
                return new ModelAndView("login", errors);
            } else {
                if (dto.isCreator()) {
                    user.setCreator(true);
                }
                session.setAttribute("user", user);
                session.setAttribute("subjects", subjectService.getAllSubjects());
                return new ModelAndView("redirect:/");
            }
        } else
            return new ModelAndView("login", errors);
    }
}
