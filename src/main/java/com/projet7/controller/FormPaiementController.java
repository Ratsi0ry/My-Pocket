package com.projet7.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import com.projet7.db.DatabaseConnection;
import com.projet7.model.Personne;

public class FormPaiementController {

    @FXML private ComboBox<Personne> cbPersonne;
    @FXML private TextField txtDiplome;
    @FXML private TextField txtMontant;
    @FXML private DatePicker dpDate;
    @FXML private Label lblErreur;
    @FXML private Button btnAnnuler;
    @FXML private Button btnEnregistrer;
    @FXML private Button btnEnregistrerEtImprimer;

    private String numTarifCourant;

    @FXML
    public void initialize() {
        dpDate.setValue(LocalDate.now());
        chargerPensionnairesVivants();

        cbPersonne.setConverter(new StringConverter<>() {
            @Override
            public String toString(Personne p) {
                return p == null ? "" : p.getIm() + " - " + p.getNom() + " " + p.getPrenoms();
            }

            @Override
            public Personne fromString(String s) {
                return null;
            }
        });

        cbPersonne.valueProperty().addListener((obs, ancien, nouveau) -> {
            if (nouveau != null) {
                chargerTarifPourDiplome(nouveau.getDiplome());
            }
        });
    }

    private void chargerPensionnairesVivants() {
        ObservableList<Personne> liste = FXCollections.observableArrayList();
        String sql = "SELECT im, nom, prenoms, datenais, diplome, contact, statut, situation, "
                + "nomconjoint, prenomconjoint FROM personne WHERE statut = true ORDER BY nom";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                liste.add(new Personne(
                        rs.getString("im"),
                        rs.getString("nom"),
                        rs.getString("prenoms"),
                        rs.getDate("datenais") != null ? rs.getDate("datenais").toLocalDate() : null,
                        rs.getString("diplome"),
                        rs.getString("contact"),
                        rs.getBoolean("statut"),
                        rs.getString("situation"),
                        rs.getString("nomconjoint"),
                        rs.getString("prenomconjoint")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblErreur.setText("Erreur lors du chargement des pensionnaires : " + e.getMessage());
        }

        cbPersonne.setItems(liste);
    }

    private void chargerTarifPourDiplome(String diplome) {
        txtDiplome.setText(diplome);
        String sql = "SELECT num_tarif, montant FROM tarif WHERE diplome = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, diplome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    numTarifCourant = rs.getString("num_tarif");
                    txtMontant.setText(String.valueOf(rs.getInt("montant")));
                } else {
                    numTarifCourant = null;
                    txtMontant.clear();
                    lblErreur.setText("Aucun tarif trouvé pour ce diplôme.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblErreur.setText("Erreur lors du calcul du montant : " + e.getMessage());
        }
    }

    @FXML
    private void onEnregistrer() {
        enregistrerPaiement(false);
    }

    @FXML
    private void onEnregistrerEtImprimer() {
        enregistrerPaiement(true);
    }

    private void enregistrerPaiement(boolean rappelRecu) {
        lblErreur.setText("");

        if (cbPersonne.getValue() == null) {
            lblErreur.setText("Sélectionne un pensionnaire.");
            return;
        }
        if (numTarifCourant == null) {
            lblErreur.setText("Aucun tarif applicable pour ce pensionnaire.");
            return;
        }
        if (dpDate.getValue() == null) {
            lblErreur.setText("Sélectionne une date de paiement.");
            return;
        }

        String sql = "INSERT INTO payer (im, num_tarif, date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cbPersonne.getValue().getIm());
            ps.setString(2, numTarifCourant);
            ps.setDate(3, Date.valueOf(dpDate.getValue()));
            ps.executeUpdate();

            Stage stage = (Stage) btnEnregistrer.getScene().getWindow();
            stage.close();

            if (rappelRecu) {
                Alert info = new Alert(AlertType.INFORMATION,
                        "Paiement enregistré. Retourne dans la liste des paiements et utilise "
                        + "« Générer reçu (PDF) » sur la ligne correspondante pour imprimer le reçu.");
                info.showAndWait();
            }

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
