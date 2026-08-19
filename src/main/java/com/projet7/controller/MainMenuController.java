package com.projet7.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Contrôleur du menu principal (MainMenu.fxml).
 * Charge dynamiquement les autres pages dans le StackPane central.
 */
public class MainMenuController {

    @FXML private Button btnPersonnes;
    @FXML private Button btnTarifs;
    @FXML private Button btnPaiements;
    @FXML private Button btnStatistiques;
    @FXML private Button btnQuitter;
    @FXML private StackPane contentArea;

    // Adapter ces chemins si tes FXML sont ailleurs dans src/main/resources
    private static final String FXML_PERSONNES = "/fxml/ListePensionnaires.fxml";
    private static final String FXML_TARIFS = "/fxml/ListeTarifs.fxml";
    private static final String FXML_PAIEMENTS = "/fxml/ListePaiements.fxml";
    private static final String FXML_STATISTIQUES = "/fxml/Statistiques.fxml";

    @FXML
    public void initialize() {
        // Rien de spécial au démarrage : le message d'accueil par défaut reste affiché.
    }

    @FXML
    private void onAfficherPersonnes() {
        chargerPage(FXML_PERSONNES);
    }

    @FXML
    private void onAfficherTarifs() {
        chargerPage(FXML_TARIFS);
    }

    @FXML
    private void onAfficherPaiements() {
        chargerPage(FXML_PAIEMENTS);
    }

    @FXML
    private void onAfficherStatistiques() {
        chargerPage(FXML_STATISTIQUES);
    }

    @FXML
    private void onQuitter() {
        Stage stage = (Stage) btnQuitter.getScene().getWindow();
        stage.close();
    }

    private void chargerPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent page = loader.load();

            contentArea.getChildren().clear();
            contentArea.getChildren().add(page);
        } catch (IOException e) {
            e.printStackTrace();
            afficherErreur("Impossible de charger la page : " + fxmlPath
                    + "\nVérifie que le fichier existe dans src/main/resources" + fxmlPath);
        }
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(AlertType.ERROR, message);
        alert.setTitle("Erreur de chargement");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
