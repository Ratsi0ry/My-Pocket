package com.projet7.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.projet7.db.DatabaseConnection;
import com.projet7.model.Tarif;

public class FormTarifController {

    @FXML private Label lblTitre;
    @FXML private TextField txtNumTarif;
    @FXML private TextField txtDiplome;
    @FXML private TextField txtCategorie;
    @FXML private TextField txtMontant;
    @FXML private Label lblErreur;
    @FXML private Button btnAnnuler;
    @FXML private Button btnEnregistrer;

    private boolean modeModification = false;

    /** Appelée depuis ListeTarifsController. Passer null pour un ajout. */
    public void setTarif(Tarif tarif) {
        modeModification = tarif != null;
        if (modeModification) {
            lblTitre.setText("Modifier un tarif");
            txtNumTarif.setText(tarif.getNumTarif());
            txtNumTarif.setEditable(false); // clé primaire, non modifiable
            txtDiplome.setText(tarif.getDiplome());
            txtCategorie.setText(tarif.getCategorie());
            txtMontant.setText(String.valueOf(tarif.getMontant()));
        } else {
            lblTitre.setText("Ajouter un tarif");
        }
    }

    @FXML
    private void onEnregistrer() {
        if (!validerFormulaire()) {
            return;
        }

        String sql = modeModification
                ? "UPDATE tarif SET diplome=?, categorie=?, montant=? WHERE num_tarif=?"
                : "INSERT INTO tarif (diplome, categorie, montant, num_tarif) VALUES (?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, txtDiplome.getText().trim());
            ps.setString(2, txtCategorie.getText().trim());
            ps.setInt(3, Integer.parseInt(txtMontant.getText().trim()));
            ps.setString(4, txtNumTarif.getText().trim());

            ps.executeUpdate();

            Stage stage = (Stage) btnEnregistrer.getScene().getWindow();
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

    private boolean validerFormulaire() {
        lblErreur.setText("");

        if (txtNumTarif.getText().isBlank() || txtDiplome.getText().isBlank() || txtCategorie.getText().isBlank()) {
            lblErreur.setText("Tous les champs sont obligatoires.");
            return false;
        }
        try {
            Integer.parseInt(txtMontant.getText().trim());
        } catch (NumberFormatException e) {
            lblErreur.setText("Le montant doit être un nombre entier.");
            return false;
        }
        return true;
    }
}
