package com.gelection.appgestionelection.services;

import com.gelection.appgestionelection.dao.ArchiveCandidat;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class GestionArchiveCandidat {

    private final IGestionArchiveCandidat igac;

    public void ajouterArchiveCandidat(ArchiveCandidat ac){
        igac.save(ac);
    }

    public void supprimerArchiveCandidat(Integer idArchiveCandidat){
        igac.deleteById(idArchiveCandidat);
    }

    public void modifierArchiveCandidat(ArchiveCandidat ac){
        igac.save(ac);
    }

    public ArchiveCandidat chercherArchiveCandidat(Integer idArchiveCandidat){
        return igac.findById(idArchiveCandidat).stream().findFirst().orElse(null);
    }

    public List<ArchiveCandidat> afficherArchiveCandidats(){
        return igac.findAll();
    }
}
