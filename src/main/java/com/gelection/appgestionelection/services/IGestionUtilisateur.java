package com.gelection.appgestionelection.services;

import com.gelection.appgestionelection.dao.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGestionUtilisateur extends JpaRepository<Utilisateur, Integer> {
}
