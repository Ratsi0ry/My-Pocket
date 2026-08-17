package com.projet7.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class ListeTarifsController {

    @FXML
    private TableView<?> tarifsTable;
    
    @FXML
    private Button btnAjouter;
    
    @FXML
    private Button btnModifier;
    
    @FXML
    private Button btnSupprimer;

    @FXML
    public void initialize() {
        // Initialisation du contrôleur
        loadTarifs();
    }

    private void loadTarifs() {
        System.out.println("Chargement de la liste des tarifs");
    }

    @FXML
    private void handleAjouter() {
        System.out.println("Ajout d'un tarif");
    }

    @FXML
    private void handleModifier() {
        System.out.println("Modification d'un tarif");
    }

    @FXML
    private void handleSupprimer() {
        System.out.println("Suppression d'un tarif");
    }
}
