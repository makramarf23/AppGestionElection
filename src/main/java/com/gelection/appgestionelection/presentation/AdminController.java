package com.gelection.appgestionelection.presentation;

import com.gelection.appgestionelection.dao.ArchiveCandidat;
import com.gelection.appgestionelection.dao.Candidat;
import com.gelection.appgestionelection.dao.Utilisateur;
import com.gelection.appgestionelection.services.GestionArchiveCandidat;
import com.gelection.appgestionelection.services.GestionCandidat;
import com.gelection.appgestionelection.services.GestionUtilisateur;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@Data
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private HttpSession httpSession;

    private final GestionCandidat gc;
    private final GestionUtilisateur gu;
    private final GestionArchiveCandidat gac;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        Integer userId = (Integer) httpSession.getAttribute("userId");
        String role = (String) httpSession.getAttribute("role");

        if(userId == null || role.equals("Electeur")){
            return "authentification";
        }

        List<Candidat> candidats = gc.afficherCandidats();

        model.addAttribute("candidats", candidats);
        model.addAttribute("title", "Tableau de Bord de l'admin");

        return "admin/acceuil";
    }

    @GetMapping("/archiveCandidat")
    public String archiveCandidat(Model model) {

        Integer userId = (Integer) httpSession.getAttribute("userId");
        String role = (String) httpSession.getAttribute("role");

        if(userId == null || role.equals("Electeur")){
            return "authentification";
        }

        List<ArchiveCandidat> archivecandidats = gac.afficherArchiveCandidats();

        model.addAttribute("archivecandidats", archivecandidats);
        model.addAttribute("title", "Liste des Candidats Archivés");

        return "admin/archiveCandidat";
    }

    @PostMapping("/ajouterCandidat")
    public String ajouterCandidat(@RequestParam("nomCandidat") String nomCandidat) {

        Integer userId = (Integer) httpSession.getAttribute("userId");
        String role = (String) httpSession.getAttribute("role");

        if(userId == null || role.equals("Electeur")){
            return "authentification";
        }

        Candidat newCandidat = new Candidat();
        newCandidat.setCandidate(nomCandidat);
        newCandidat.setVotes(0);
        newCandidat.setAffC(false);
        gc.ajouterCandidat(newCandidat);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/modifierCandidat")
    public String modifierCandidat(@RequestParam("idCandidat") int idCandidat, @RequestParam("nomCandidat") String nomCandidat) {

        Integer userId = (Integer) httpSession.getAttribute("userId");
        String role = (String) httpSession.getAttribute("role");

        if(userId == null || role.equals("Electeur")){
            return "authentification";
        }

        Candidat fc = gc.chercherCandidat(idCandidat);
        if (fc != null) {
            fc.setCandidate(nomCandidat);
            gc.modifierCandidat(fc);
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/supprimerCandidat")
    public String supprimerCandidat(@RequestParam("idCandidat") int idCandidat) {

        Integer userId = (Integer) httpSession.getAttribute("userId");
        String role = (String) httpSession.getAttribute("role");

        if(userId == null || role.equals("Electeur")){
            return "authentification";
        }

        gc.supprimerCandidat(idCandidat);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/afficherResultat")
    public String changeAffC() {

        Integer userId = (Integer) httpSession.getAttribute("userId");
        String role = (String) httpSession.getAttribute("role");

        if(userId == null || role.equals("Electeur")){
            return "authentification";
        }

        for(Candidat c : gc.afficherCandidats()){
            c.setAffC(true);
            gc.modifierCandidat(c);
        }

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/rinitialiserElections")
    public String resetElections() {

        Integer userId = (Integer) httpSession.getAttribute("userId");
        String role = (String) httpSession.getAttribute("role");

        if(userId == null || role.equals("Electeur")){
            return "authentification";
        }

        Optional<Candidat> oc = gc.afficherCandidats().stream().max(Comparator.comparingInt(Candidat::getVotes));

        if (oc.isPresent()) {
            Candidat c = oc.get();

            gac.ajouterArchiveCandidat(new ArchiveCandidat(-99, c.getCandidate(), c.getVotes()));
        }

        for(Candidat c : gc.afficherCandidats()){
            gc.supprimerCandidat(c.getIdCandidat());
        }

        for(Utilisateur u : gu.afficherUtilisateurs()){
            u.setStatus("Non Vote");
            gu.modifierUtilisateur(u);
        }

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/logout")
    public String logoutAdmin() {

        Integer userId = (Integer) httpSession.getAttribute("userId");
        String role = (String) httpSession.getAttribute("role");

        if(userId == null || role.equals("Electeur")){
            return "authentification";
        }

        httpSession.invalidate();
        return "redirect:/authentification";
    }
}
