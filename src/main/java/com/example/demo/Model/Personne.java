package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "personne")
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

    @OneToOne(mappedBy = "personne", cascade = CascadeType.ALL)
    private Conjoint conjoint;

    //getters
    public String getIm(){
        return im;
    }

    public String getNom(){
        return nom;
    }

    public String getPrenom(){
        return prenom;
    }

    public String getContact(){
        return contact;
    }

    public LocalDate getDateNais(){
        return dateNais;
    }

    public Boolean getStatut(){
        return statut;
    }

    public String getSituation(){
        return situation;
    }

    public Tarif getTarif() {
        return tarif;
    }

    public Conjoint getConjoint(){
        return conjoint;
    }

    //setters
    public void setIm(String i){
        this.im = i;
    }

    public void setNom(String n){
        this.nom = n;
    }

    public void setPrenom(String p){
        this.prenom = p;
    }

    public void setContact(String c){
        this.contact = c;
    }

    public void setDateNais(LocalDate d){
        this.dateNais = d;
    }

    public void setStatut(Boolean st){
        this.statut = st;
    }

    public void setSituation(String si){
        this.situation = si;
    }

    public void setTarif(Tarif tarif){
        this.tarif = tarif;
    }

    public void setConjoint(Conjoint conjoint){
        this.conjoint = conjoint;
    }
}
