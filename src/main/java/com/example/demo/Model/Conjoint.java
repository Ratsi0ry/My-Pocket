package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "conjoint")
@NoArgsConstructor
@AllArgsConstructor
public class Conjoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numpension", nullable = false, unique = true)
    private String numPension;

    private String nomConjoint;

    private String prenomConjoint;

    private Integer montant;

    private Boolean statutConjoint = true;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_im", referencedColumnName = "im", unique = true)
    private Personne personne;

    // Getters
    public Long getId() {
        return id;
    }

    public String getNumPension() {
        return numPension;
    }

    public String getNomConjoint() {
        return nomConjoint;
    }

    public String getPrenomConjoint() {
        return prenomConjoint;
    }

    public Integer getMontant() {
        return montant;
    }

    public Boolean getStatutConjoint() {
        return statutConjoint;
    }

    public Personne getPersonne() {
        return personne;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNumPension(String numPension) {
        this.numPension = numPension;
    }

    public void setNomConjoint(String nomConjoint) {
        this.nomConjoint = nomConjoint;
    }

    public void setPrenomConjoint(String prenomConjoint) {
        this.prenomConjoint = prenomConjoint;
    }

    public void setMontant(Integer montant) {
        this.montant = montant;
    }

    public void setStatutConjoint(Boolean statutConjoint) {
        this.statutConjoint = statutConjoint;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }
}
