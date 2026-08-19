package com.projet7.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.projet7.db.DatabaseConnection;
import com.projet7.model.Personne;

public class DeclarerDecesController {

    @FXML private Label lblPersonne;
    @FXML private DatePicker dpDateDeces;
    @FXML private TextField txtNomConjoint;
    @FXML private TextField txtPrenomConjoint;
    @FXML private TextField txtMontantInitial;
    @FXML private TextField txtMontantConjoint;
    @FXML private Label lblErreur;
    @FXML private Button btnAnnuler;
    @FXML private Button btnConfirmer;

    private Personne personne;
    private int montantInitial;

    /** Appelée depuis ListePensionnairesController avant l'affichage du dialogue. */
    public void setPersonne(Personne personne) {
        this.personne = personne;
        lblPersonne.setText("Pensionnaire : " + personne.getIm() + " - " + personne.getNom() + " " + personne.getPrenoms());
        txtNomConjoint.setText(personne.getNomConjoint());
        txtPrenomConjoint.setText(personne.getPrenomConjoint());
        chargerMontantInitial(personne.getDiplome());
    }

    private void chargerMontantInitial(String diplome) {
        String sql = "SELECT montant FROM tarif WHERE diplome = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, diplome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    montantInitial = rs.getInt("montant");
                    txtMontantInitial.setText(String.valueOf(montantInitial));
                    txtMontantConjoint.setText(String.valueOf(Math.round(montantInitial * 0.4f)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblErreur.setText("Erreur lors du calcul du montant : " + e.getMessage());
        }
    }

    @FXML
    private void onConfirmer() {
        lblErreur.setText("");

        if (dpDateDeces.getValue() == null) {
            lblErreur.setText("Sélectionne la date de décès.");
            return;
        }
        if (txtNomConjoint.getText().isBlank() || txtPrenomConjoint.getText().isBlank()) {
            lblErreur.setText("Le nom et le prénom du conjoint sont obligatoires.");
            return;
        }

        int montantConjoint = Math.round(montantInitial * 0.4f);

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psPersonne = conn.prepareStatement(
                    "UPDATE personne SET statut = false WHERE im = ?")) {
                psPersonne.setString(1, personne.getIm());
                psPersonne.executeUpdate();
            }

            try (PreparedStatement psConjoint = conn.prepareStatement(
                    "INSERT INTO conjoint (numpension, nomconjoint, prenomconjoint, montant, im) VALUES (?, ?, ?, ?, ?)")) {
                psConjoint.setString(1, personne.getIm());
                psConjoint.setString(2, txtNomConjoint.getText().trim());
                psConjoint.setString(3, txtPrenomConjoint.getText().trim());
                psConjoint.setInt(4, montantConjoint);
                psConjoint.setString(5, personne.getIm());
                psConjoint.executeUpdate();
            }

            conn.commit();

            Stage stage = (Stage) btnConfirmer.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            e.printStackTrace();
            lblErreur.setText("Erreur lors de l'enregistrement : " + e.getMessage());
        }
    }

    @FXML
    private void onAnnuler() {
        Stage stage = (Stage) btnAnnuler.getScene().getWindow();
        stage.close();
    }
}
