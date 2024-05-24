package com.gelection.appgestionelection.services;

import com.gelection.appgestionelection.dao.Candidat;
import com.gelection.appgestionelection.dao.Utilisateur;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class GestionCandidat {
    private final IGestionCandidat igc;

    public void ajouterCandidat(Candidat c){
        igc.save(c);
    }

    public void supprimerCandidat(Integer idCandidat){
        igc.deleteById(idCandidat);
    }

    public void modifierCandidat(Candidat c){
        igc.save(c);
    }

    public Candidat chercherCandidat(Integer idCandidat){
        return igc.findById(idCandidat).stream().findFirst().orElse(null);
    }

    public List<Candidat> afficherCandidats(){
        return igc.findAll();
    }
}
