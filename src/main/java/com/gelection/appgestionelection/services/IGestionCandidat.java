package com.gelection.appgestionelection.services;

import com.gelection.appgestionelection.dao.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGestionCandidat extends JpaRepository<Candidat, Integer> {
}
