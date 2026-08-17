package com.projet7.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FormPersonneController {

    @FXML
    private TextField nomField;
    
    @FXML
    private TextField prenomField;
    
    @FXML
    private TextField dateNaissanceField;
    
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
        System.out.println("Sauvegarde de la personne");
    }

    @FXML
    private void handleAnnuler() {
        System.out.println("Annulation");
    }
}
