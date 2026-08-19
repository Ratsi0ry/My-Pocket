package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tarif")
@NoArgsConstructor
@AllArgsConstructor
public class Tarif {

    @Id
    @Column(name = "num_tarif")
    private String numTarif;

    private String diplome;

    private String categorie;

    private Integer montant;

    //getters
    public String getNumTarif(){
        return numTarif;
    }

    public String getDiplome(){
        return diplome;
    }

    public String getCategorie(){
        return categorie;
    }

    public Integer getMontant(){
        return montant;
    }

    //setters
    public void setNumTarif(String n){
        this.numTarif = n;
    }

    public void setDiplome(String d){
        this.diplome = d;
    }

    public void setCategorie(String c){
        this.categorie = c;
    }

    public void setMontant(Integer m){
        this.montant = m;
    }

}
