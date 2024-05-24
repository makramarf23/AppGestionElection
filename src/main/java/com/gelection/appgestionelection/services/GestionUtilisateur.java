package com.gelection.appgestionelection.services;

import com.gelection.appgestionelection.dao.Utilisateur;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class GestionUtilisateur {
    private final IGestionUtilisateur igu;

    public void ajouterUtilisateur(Utilisateur u){
        igu.save(u);
    }

    public void supprimerUtilisateur(Integer idUtilisateur){
        igu.deleteById(idUtilisateur);
    }

    public void modifierUtilisateur(Utilisateur u){
        igu.save(u);
    }

    public Utilisateur chercherUtilisateur(Integer idUtilisateur){
        return igu.findById(idUtilisateur).stream().findFirst().orElse(null);
    }

    public List<Utilisateur> afficherUtilisateurs(){
        return igu.findAll();
    }
}
