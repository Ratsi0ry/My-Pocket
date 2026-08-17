package com.projet7.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ListePensionnairesController {

    @FXML
    private TableView<?> pensionnairesTable;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private Button btnAjouter;
    
    @FXML
    private Button btnModifier;
    
    @FXML
    private Button btnSupprimer;

    @FXML
    public void initialize() {
        // Initialisation du contrôleur
        loadPensionnaires();
    }

    private void loadPensionnaires() {
        System.out.println("Chargement de la liste des pensionnaires");
    }

    @FXML
    private void handleAjouter() {
        System.out.println("Ajout d'un pensionnaire");
    }

    @FXML
    private void handleModifier() {
        System.out.println("Modification d'un pensionnaire");
    }

    @FXML
    private void handleSupprimer() {
        System.out.println("Suppression d'un pensionnaire");
    }

    @FXML
    private void handleRecherche() {
        System.out.println("Recherche de pensionnaires");
    }
}
