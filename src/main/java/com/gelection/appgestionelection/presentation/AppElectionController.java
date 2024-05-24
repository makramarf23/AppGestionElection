package com.gelection.appgestionelection.presentation;

import com.gelection.appgestionelection.dao.Utilisateur;
import com.gelection.appgestionelection.services.GestionUtilisateur;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Data
public class AppElectionController {

    private final GestionUtilisateur gu;

    @GetMapping("/")
    public String home(Model m) {
        m.addAttribute("title","Election Acceuil");
        return "acceuil";
    }

    @GetMapping("/authentification")
    public String login(Model m) {
        m.addAttribute("title", "Election Authentification");
        return "authentification";
    }

    @GetMapping("/inscription")
    public String register(Model m) {
        m.addAttribute("title","Election Inscription");
        return "inscription";
    }

    @PostMapping("/creerUtilisateur")
    public String creerUtilisateur(@ModelAttribute Utilisateur utilisateur, RedirectAttributes redirectAttributes) {
        utilisateur.setRole("Electeur");
        utilisateur.setStatus("Non Vote");
        gu.ajouterUtilisateur(utilisateur);

        redirectAttributes.addFlashAttribute("message", "Utilisateur créé avec succès !");
        return "redirect:/authentification";
    }

    @PostMapping("/authentification")
    public String authentifierUtilisateur(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, RedirectAttributes redirectAttributes) {
        Utilisateur utilisateur = gu.afficherUtilisateurs().stream().filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password)).findFirst().orElse(null);

        if (utilisateur != null) {
            session.setAttribute("userId", utilisateur.getIdUtilisateur());
            session.setAttribute("role", utilisateur.getRole());

            if(utilisateur.getRole().equals("Admin")){
                return "redirect:/admin/dashboard";
            }
            return "redirect:/electeur/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("erreur", "Email ou mot de passe incorrect.");
            return "redirect:/authentification";
        }
    }
}
