package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tarif")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Tarif {
    @Id
    private String numTarif;

    private String diplome;
    private String categorie;
    private Integer montant;
}
