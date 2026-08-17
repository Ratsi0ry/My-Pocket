package com.projet7.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class MainMenuController {

    @FXML
    private Button btnPersonnes;
    
    @FXML
    private Button btnTarifs;
    
    @FXML
    private Button btnPaiements;
    
    @FXML
    private Button btnStatistiques;
    
    @FXML
    private Button btnQuitter;
    
    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        // Initialisation du contrôleur
    }

    @FXML
    private void onAfficherPersonnes() {
        System.out.println("Affichage de la liste des pensionnaires");
    }

    @FXML
    private void onAfficherTarifs() {
        System.out.println("Affichage de la liste des tarifs");
    }

    @FXML
    private void onAfficherPaiements() {
        System.out.println("Affichage de la liste des paiements");
    }

    @FXML
    private void onAfficherStatistiques() {
        System.out.println("Affichage des statistiques");
    }

    @FXML
    private void onQuitter() {
        System.out.println("Fermeture de l'application");
        System.exit(0);
    }
}
