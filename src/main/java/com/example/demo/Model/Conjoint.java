package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "conjoint")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conjoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numPension;

    private String nomConjoint;
    private String prenomConjoint;

    private Integer montant;

    private Boolean statutConjoint = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_im", referencedColumnName = "im")
    private Personne personne;

    public Boolean getStatutConjoint() {
        return statutConjoint;
    }

    public void setStatutConjoint(Boolean statutConjoint) {
        this.statutConjoint = statutConjoint;
    }
}