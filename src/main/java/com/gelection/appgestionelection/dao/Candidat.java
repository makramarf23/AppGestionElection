package com.gelection.appgestionelection.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candidat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCandidat;
    @Column(unique = true)
    private String candidate;
    private int votes;
    private boolean affC;
}
