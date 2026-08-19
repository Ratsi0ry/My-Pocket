package com.projet7.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Modèle correspondant à la table PERSONNE.
 * Les noms des getters correspondent aux PropertyValueFactory utilisés dans les FXML
 * (colonnes des TableView "im", "nom", "prenoms", "datenais", "diplome", "contact",
 * "statut", "situation", "nomConjoint", "prenomConjoint").
 */
public class Personne {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String im;
    private String nom;
    private String prenoms;
    private LocalDate dateNaissance;
    private String diplome;
    private String contact;
    private boolean statutVivant;
    private String situation;
    private String nomConjoint;
    private String prenomConjoint;

    public Personne(String im, String nom, String prenoms, LocalDate dateNaissance, String diplome,
                     String contact, boolean statutVivant, String situation,
                     String nomConjoint, String prenomConjoint) {
        this.im = im;
        this.nom = nom;
        this.prenoms = prenoms;
        this.dateNaissance = dateNaissance;
        this.diplome = diplome;
        this.contact = contact;
        this.statutVivant = statutVivant;
        this.situation = situation;
        this.nomConjoint = nomConjoint;
        this.prenomConjoint = prenomConjoint;
    }

    public String getIm() { return im; }
    public void setIm(String im) { this.im = im; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenoms() { return prenoms; }
    public void setPrenoms(String prenoms) { this.prenoms = prenoms; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    /** Utilisé par la colonne TableView "datenais" (affichage formaté). */
    public String getDatenais() {
        return dateNaissance != null ? dateNaissance.format(FORMAT) : "";
    }

    public String getDiplome() { return diplome; }
    public void setDiplome(String diplome) { this.diplome = diplome; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    /** Utilisé par la colonne TableView "statut" (affichage texte). */
    public String getStatut() { return statutVivant ? "Vivant" : "Décédé"; }

    public boolean isStatutVivant() { return statutVivant; }
    public void setStatutVivant(boolean statutVivant) { this.statutVivant = statutVivant; }

    public String getSituation() { return situation; }
    public void setSituation(String situation) { this.situation = situation; }

    public String getNomConjoint() { return nomConjoint; }
    public void setNomConjoint(String nomConjoint) { this.nomConjoint = nomConjoint; }

    public String getPrenomConjoint() { return prenomConjoint; }
    public void setPrenomConjoint(String prenomConjoint) { this.prenomConjoint = prenomConjoint; }
}
