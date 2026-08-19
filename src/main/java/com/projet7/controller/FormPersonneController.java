package com.projet7.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.projet7.db.DatabaseConnection;
import com.projet7.model.Personne;

public class FormPersonneController {

    @FXML private Label lblTitre;
    @FXML private TextField txtIM;
    @FXML private TextField txtNom;
    @FXML private TextField txtPrenoms;
    @FXML private DatePicker dpDateNais;
    @FXML private ComboBox<String> cbDiplome;
    @FXML private TextField txtContact;
    @FXML private ComboBox<String> cbStatut;
    @FXML private ComboBox<String> cbSituation;
    @FXML private TextField txtNomConjoint;
    @FXML private TextField txtPrenomConjoint;
    @FXML private Label lblErreur;
    @FXML private Button btnAnnuler;
    @FXML private Button btnEnregistrer;

    private boolean modeModification = false;

    @FXML
    public void initialize() {
        cbStatut.setItems(FXCollections.observableArrayList("Vivant", "Décédé"));
        cbSituation.setItems(FXCollections.observableArrayList("Marié(e)", "Divorcé(e)", "Veuf(ve)"));
        chargerDiplomes();
    }

    /** Appelée depuis ListePensionnairesController. Passer null pour un ajout. */
    public void setPersonne(Personne personne) {
        modeModification = personne != null;

        if (modeModification) {
            lblTitre.setText("Modifier un pensionnaire");
            txtIM.setText(personne.getIm());
            txtIM.setEditable(false); // clé primaire, non modifiable
            txtNom.setText(personne.getNom());
            txtPrenoms.setText(personne.getPrenoms());
            dpDateNais.setValue(personne.getDateNaissance());
            cbDiplome.setValue(personne.getDiplome());
            txtContact.setText(personne.getContact());
            cbStatut.setValue(personne.getStatut());
            cbSituation.setValue(personne.getSituation());
            txtNomConjoint.setText(personne.getNomConjoint());
            txtPrenomConjoint.setText(personne.getPrenomConjoint());
        } else {
            lblTitre.setText("Ajouter un pensionnaire");
        }
    }

    private void chargerDiplomes() {
        String sql = "SELECT DISTINCT diplome FROM tarif ORDER BY diplome";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cbDiplome.getItems().add(rs.getString("diplome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblErreur.setText("Impossible de charger la liste des diplômes (vérifie la table tarif).");
        }
    }

    @FXML
    private void onEnregistrer() {
        if (!validerFormulaire()) {
            return;
        }

        String sql = modeModification
                ? "UPDATE personne SET nom=?, prenoms=?, datenais=?, diplome=?, contact=?, statut=?, "
                  + "situation=?, nomconjoint=?, prenomconjoint=? WHERE im=?"
                : "INSERT INTO personne (nom, prenoms, datenais, diplome, contact, statut, situation, "
                  + "nomconjoint, prenomconjoint, im) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, txtNom.getText().trim());
            ps.setString(2, txtPrenoms.getText().trim());
            ps.setDate(3, Date.valueOf(dpDateNais.getValue()));
            ps.setString(4, cbDiplome.getValue());
            ps.setString(5, txtContact.getText().trim());
            ps.setBoolean(6, cbStatut.getValue().equals("Vivant"));
            ps.setString(7, cbSituation.getValue());
            ps.setString(8, txtNomConjoint.getText().trim());
            ps.setString(9, txtPrenomConjoint.getText().trim());
            ps.setString(10, txtIM.getText().trim());

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

        if (txtIM.getText().isBlank() || txtNom.getText().isBlank() || txtPrenoms.getText().isBlank()) {
            lblErreur.setText("L'IM, le nom et les prénoms sont obligatoires.");
            return false;
        }
        if (dpDateNais.getValue() == null) {
            lblErreur.setText("La date de naissance est obligatoire.");
            return false;
        }
        if (cbDiplome.getValue() == null) {
            lblErreur.setText("Sélectionne un diplôme.");
            return false;
        }
        if (cbStatut.getValue() == null) {
            lblErreur.setText("Sélectionne un statut.");
            return false;
        }
        if (cbSituation.getValue() == null) {
            lblErreur.setText("Sélectionne une situation.");
            return false;
        }
        return true;
    }
}
