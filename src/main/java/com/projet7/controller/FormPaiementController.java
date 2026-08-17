package com.projet7.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class FormPaiementController {

    @FXML
    private ComboBox<?> pensionnaireCombo;
    
    @FXML
    private TextField montantField;
    
    @FXML
    private DatePicker datePaiementPicker;
    
    @FXML
    private TextField modePaymentField;
    
    @FXML
    private Button btnSauvegarder;
    
    @FXML
    private Button btnAnnuler;
    
    @FXML
    private Button btnGenererReceipt;

    @FXML
    public void initialize() {
        // Initialisation du contrôleur
    }

    @FXML
    private void handleSauvegarder() {
        System.out.println("Sauvegarde du paiement");
    }

    @FXML
    private void handleAnnuler() {
        System.out.println("Annulation");
    }

    @FXML
    private void handleGenererReceipt() {
        System.out.println("Génération du reçu de paiement");
    }
}
