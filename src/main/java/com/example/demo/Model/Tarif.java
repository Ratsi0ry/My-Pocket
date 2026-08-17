package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tarif")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarif {

    @Id
    @Column(name = "numtarif")
    private String numTarif;

    private String diplome;

    private String categorie;

    private Integer montant;
}