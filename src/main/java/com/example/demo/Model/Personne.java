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
    private String im;

    private String nom;

    private String prenom;

    private String contact;

    @Column(name = "datenais")
    private LocalDate dateNais;

    private Boolean statut = true;

    @Column(name = "situation")
    private String situation;

    //FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_numtarif", referencedColumnName = "numtarif")
    private Tarif tarif;

    @OneToMany(mappedBy = "personne", cascade = CascadeType.ALL)
    private List<Conjoint> conjoints;
}
