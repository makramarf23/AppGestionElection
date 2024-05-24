package com.gelection.appgestionelection.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveCandidat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idArchiveCandidat;
    @Column(unique = true)
    private String candidatGagnant;
    private int votes;
}
