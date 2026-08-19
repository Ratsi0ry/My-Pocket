package com.projet7.model;

/**
 * Modèle représentant une ligne de paiement affichée dans ListePaiements.fxml
 * (résultat d'une jointure entre PAYER, PERSONNE et TARIF).
 */
public class Paiement {

    private String im;
    private String nomPrenoms;
    private String numTarif;
    private int montant;
    private String date; // formatée dd/MM/yyyy pour l'affichage

    public Paiement(String im, String nomPrenoms, String numTarif, int montant, String date) {
        this.im = im;
        this.nomPrenoms = nomPrenoms;
        this.numTarif = numTarif;
        this.montant = montant;
        this.date = date;
    }

    public String getIm() { return im; }
    public String getNomPrenoms() { return nomPrenoms; }
    public String getNumTarif() { return numTarif; }
    public int getMontant() { return montant; }
    public String getDate() { return date; }
}
