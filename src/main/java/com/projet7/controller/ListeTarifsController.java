package com.projet7.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.projet7.db.DatabaseConnection;
import com.projet7.model.Tarif;

public class ListeTarifsController {

    @FXML private TextField txtRecherche;
    @FXML private Button btnRechercher;
    @FXML private Button btnReinitialiser;
    @FXML private Button btnAjouter;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;

    @FXML private TableView<Tarif> tableTarifs;
    @FXML private TableColumn<Tarif, String> colNumTarif;
    @FXML private TableColumn<Tarif, String> colDiplome;
    @FXML private TableColumn<Tarif, String> colCategorie;
    @FXML private TableColumn<Tarif, Number> colMontant;

    @FXML private Label lblTotal;

    private final ObservableList<Tarif> listeTarifs = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        chargerTarifs(null);
    }

    private void chargerTarifs(String recherche) {
        listeTarifs.clear();

        String sql = "SELECT num_tarif, diplome, categorie, montant FROM tarif "
                + (recherche != null && !recherche.isBlank() ? "WHERE diplome ILIKE ? OR num_tarif ILIKE ? " : "")
                + "ORDER BY diplome";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (recherche != null && !recherche.isBlank()) {
                ps.setString(1, "%" + recherche + "%");
                ps.setString(2, "%" + recherche + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    listeTarifs.add(new Tarif(
                            rs.getString("num_tarif"),
                            rs.getString("diplome"),
                            rs.getString("categorie"),
                            rs.getInt("montant")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            afficherErreur("Erreur lors du chargement des tarifs : " + e.getMessage());
        }

        tableTarifs.setItems(listeTarifs);
        lblTotal.setText("Nombre de tarifs : " + listeTarifs.size());
    }

    @FXML
    private void onRechercher() {
        chargerTarifs(txtRecherche.getText());
    }

    @FXML
    private void onReinitialiser() {
        txtRecherche.clear();
        chargerTarifs(null);
    }

    @FXML
    private void onAjouter() {
        ouvrirFormulaire(null);
    }

    @FXML
    private void onModifier() {
        Tarif selection = tableTarifs.getSelectionModel().getSelectedItem();
        if (selection == null) {
            afficherErreur("Sélectionne un tarif à modifier.");
            return;
        }
        ouvrirFormulaire(selection);
    }

    @FXML
    private void onSupprimer() {
        Tarif selection = tableTarifs.getSelectionModel().getSelectedItem();
        if (selection == null) {
            afficherErreur("Sélectionne un tarif à supprimer.");
            return;
        }

        Alert confirmation = new Alert(AlertType.CONFIRMATION,
                "Supprimer le tarif " + selection.getNumTarif() + " (" + selection.getDiplome() + ") ?");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String sql = "DELETE FROM tarif WHERE num_tarif = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, selection.getNumTarif());
                ps.executeUpdate();
                chargerTarifs(txtRecherche.getText());
            } catch (SQLException e) {
                e.printStackTrace();
                afficherErreur("Erreur lors de la suppression : " + e.getMessage());
            }
        }
    }

    private void ouvrirFormulaire(Tarif tarif) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormTarif.fxml"));
            Parent root = loader.load();

            FormTarifController controller = loader.getController();
            controller.setTarif(tarif);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(tarif == null ? "Ajouter un tarif" : "Modifier un tarif");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            chargerTarifs(txtRecherche.getText());
        } catch (IOException e) {
            e.printStackTrace();
            afficherErreur("Impossible d'ouvrir le formulaire : " + e.getMessage());
        }
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(AlertType.ERROR, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
