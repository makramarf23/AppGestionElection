package com.gelection.appgestionelection.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idUtilisateur;
    @Column(unique=true)
    private String email;
    private String name;
    private String password;
    private int phone;
    private String status;
    private String role;
}
