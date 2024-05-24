package com.gelection.appgestionelection.presentation;

import com.gelection.appgestionelection.dao.Candidat;
import com.gelection.appgestionelection.dao.Utilisateur;
import com.gelection.appgestionelection.services.GestionCandidat;
import com.gelection.appgestionelection.services.GestionUtilisateur;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Data
@RequestMapping("/electeur")
public class ElecteurController {

    @Autowired
    private HttpSession httpSession;

    private final GestionCandidat gc;
    private final GestionUtilisateur gu;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        Integer userId = (Integer) httpSession.getAttribute("userId");
        String role = (String) httpSession.getAttribute("role");

        if(userId == null || role.equals("Admin")){
            return "authentification";
        }

        List<Candidat> candidats = gc.afficherCandidats();

        model.addAttribute("candidats", candidats);
        model.addAttribute("title", "Tableau de Bord de l'Électeur");

        return "electeur/acceuil";
    }

    @PostMapping("/voteCandidat")
    public String voter(@RequestParam("idCandidat") int idCandidat) {

        Integer userId = (Integer) httpSession.getAttribute("userId");
        String role = (String) httpSession.getAttribute("role");

        if(userId == null || role.equals("Admin")){
            return "authentification";
        }

        Utilisateur u = gu.chercherUtilisateur(userId);

        if(u.getStatus().equals("Non Vote")){
            u.setStatus("Vote");
            Candidat c = gc.chercherCandidat(idCandidat);
            c.setVotes(c.getVotes()+1);
            gc.modifierCandidat(c);
        }

        return "redirect:/electeur/dashboard";
    }

    @GetMapping("/logout")
    public String logoutElecteur() {

        Integer userId = (Integer) httpSession.getAttribute("userId");
        String role = (String) httpSession.getAttribute("role");

        if(userId == null || role.equals("Admin")){
            return "authentification";
        }

        httpSession.invalidate();
        return "redirect:/authentification";
    }
}
