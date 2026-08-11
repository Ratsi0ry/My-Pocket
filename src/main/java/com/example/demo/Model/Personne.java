package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "personne")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Personne {
    @Id
    private String IM;

    private String nom, prenom, contact;

    private LocalDate dateNais;

    private Boolean statut = true;

    private String situation;

    //FK
    @ManyToOne
    @JoinColumn(name = "fk_numtarif", referencedColumnName = "numtarif")
    private Tarif tarif;


    public Personne getPersonneById(String im) {
        return null;
    }

    public void deletePersonne(String im) {
    }

    public Personne savePersonne(Personne personne) {
        return personne;
    }

    public List<Personne> getAllPersonnes() {
        return List.of();
    }
}
