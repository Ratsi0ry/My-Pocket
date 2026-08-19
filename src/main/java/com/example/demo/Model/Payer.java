package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "payer")
@NoArgsConstructor
@AllArgsConstructor
public class Payer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate datePaiement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "num_tarif", referencedColumnName = "num_tarif")
    private Tarif tarif;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "im", referencedColumnName = "im")
    private Personne personne;

    //getters
    public long getId (){
        return id;
    }

    public LocalDate getDatePaiement(){
        return datePaiement;
    }

    public Tarif getTarif(){
        return tarif;
    }

    public Personne getPersonne(){
        return personne;
    }

    //setters
    public void setId(long i){
        this.id = i;
    }

    public void setDatePaiement(LocalDate l){
        this.datePaiement = l;
    }

    public void setTarif(Tarif tarif){
        this.tarif = tarif;
    }

    public void setPersonne(Personne personne){
        this.personne = personne;
    }
}
