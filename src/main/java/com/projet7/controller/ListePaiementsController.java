package com.projet7.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ListePaiementsController {

    @FXML
    private TableView<?> paiementsTable;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private DatePicker dateDebut;
    
    @FXML
    private DatePicker dateFin;
    
    @FXML
    private Button btnAjouter;
    
    @FXML
    private Button btnModifier;
    
    @FXML
    private Button btnSupprimer;
    
    @FXML
    private Button btnExporter;

    @FXML
    public void initialize() {
        // Initialisation du contrôleur
        loadPaiements();
    }

    private void loadPaiements() {
        System.out.println("Chargement de la liste des paiements");
    }

    @FXML
    private void handleAjouter() {
        System.out.println("Ajout d'un paiement");
    }

    @FXML
    private void handleModifier() {
        System.out.println("Modification d'un paiement");
    }

    @FXML
    private void handleSupprimer() {
        System.out.println("Suppression d'un paiement");
    }

    @FXML
    private void handleExporter() {
        System.out.println("Exportation des paiements");
    }

    @FXML
    private void handleRecherche() {
        System.out.println("Recherche de paiements");
    }

    @FXML
    private void handleFiltrage() {
        System.out.println("Filtrage par dates");
    }
}
