package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "conjoint")
@NoArgsConstructor
@AllArgsConstructor

public class Conjoint {
    @Id
    private String numPension;

    private String nomConjoint, prenomConjoint;

    private Integer Montant;

    private Boolean statutConjoint = true;

    @ManyToOne
    @JoinColumn(name = "fk_im", referencedColumnName = "im")
    private Personne personne;

}
