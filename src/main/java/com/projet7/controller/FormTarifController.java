package com.projet7.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FormTarifController {

    @FXML
    private TextField descriptionField;
    
    @FXML
    private TextField montantField;
    
    @FXML
    private Button btnSauvegarder;
    
    @FXML
    private Button btnAnnuler;

    @FXML
    public void initialize() {
        // Initialisation du contrôleur
    }

    @FXML
    private void handleSauvegarder() {
        System.out.println("Sauvegarde du tarif");
    }

    @FXML
    private void handleAnnuler() {
        System.out.println("Annulation");
    }
}
