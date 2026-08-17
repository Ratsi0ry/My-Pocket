package com.projet7.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

public class DeclarerDecesController {

    @FXML
    private ComboBox<?> pensionnaireCombo;
    
    @FXML
    private DatePicker dateDecesPicke;
    
    @FXML
    private TextArea observationsArea;
    
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
        System.out.println("Enregistrement du décès");
    }

    @FXML
    private void handleAnnuler() {
        System.out.println("Annulation");
    }
}
