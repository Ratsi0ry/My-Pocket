package com.projet7.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public class StatistiquesController {

    @FXML
    private Label totalPensionnairesLabel;
    
    @FXML
    private Label totalRevenusLabel;
    
    @FXML
    private Label moyenneMensuelleLabel;
    
    @FXML
    private BarChart<String, Number> barChartRevenue;
    
    @FXML
    private LineChart<String, Number> lineChartTrend;

    @FXML
    public void initialize() {
        // Initialisation du contrôleur
        loadStatistiques();
    }

    private void loadStatistiques() {
        System.out.println("Chargement des statistiques");
        // À implémenter : calcul des statistiques
    }
}
