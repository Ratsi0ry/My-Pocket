package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payer")
@NoArgsConstructor
@AllArgsConstructor
public class Payer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_paiement", nullable = false)
    private LocalDateTime datePaiement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_numtarif", referencedColumnName = "numtarif")
    private Tarif tarif;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_im", referencedColumnName = "im")
    private Personne personne;

    //getters
    public long getId (){
        return id;
    }

    public LocalDateTime getDatePaiement(){
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

    public void setDatePaiement(LocalDateTime l){
        this.datePaiement = l;
    }

    public void setTarif(Tarif tarif){
        this.tarif = tarif;
    }

    public void setPersonne(Personne personne){
        this.personne = personne;
    }
}