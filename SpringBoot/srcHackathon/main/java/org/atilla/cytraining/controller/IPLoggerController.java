package org.atilla.cytraining.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IPLoggerController {

    @RequestMapping("/log")
    public String logIP(HttpServletRequest request) {
        // Récupérer l'IP depuis l'en-tête X-Forwarded-For s'il y a un proxy
        String ipAddress = request.getHeader("X-Forwarded-For");
        
        if (ipAddress == null || ipAddress.isEmpty()) {
            // Si l'en-tête X-Forwarded-For est vide, on utilise l'IP directe
            ipAddress = request.getRemoteAddr();
        } else {
            // L'adresse IP dans X-Forwarded-For peut être une liste, donc on prend la première
            ipAddress = ipAddress.split(",")[0];
        }

        // Affiche l'IP dans la console (ou tu peux la stocker où tu veux, comme dans une base de données)
        System.out.println("Nouvelle connexion depuis l'adresse IP : " + ipAddress);

        // Optionnel : rediriger vers une page ou afficher un message de succès
        return "Merci de votre participation! Votre IP a été enregistrée.";
    }
}
