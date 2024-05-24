package com.gelection.appgestionelection.services;

import com.gelection.appgestionelection.dao.ArchiveCandidat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGestionArchiveCandidat extends JpaRepository<ArchiveCandidat, Integer> {
}
