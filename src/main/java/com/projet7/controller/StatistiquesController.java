package com.projet7.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import com.projet7.db.DatabaseConnection;

public class StatistiquesController {

    @FXML private ComboBox<String> cbCritere;
    @FXML private Button btnActualiser;
    @FXML private BarChart<String, Number> barChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;

    // Expression SQL utilisée selon le critère choisi (valeurs fixes, pas de saisie utilisateur)
    private static final Map<String, String> EXPRESSIONS = Map.of(
            "Statut", "CASE WHEN statut THEN 'Vivant' ELSE 'Décédé' END",
            "Diplôme", "diplome",
            "Situation", "situation"
    );

    @FXML
    public void initialize() {
        cbCritere.setItems(FXCollections.observableArrayList("Statut", "Diplôme", "Situation"));
        cbCritere.getSelectionModel().selectFirst();
        chargerStatistiques();
    }

    @FXML
    private void onChangerCritere() {
        chargerStatistiques();
    }

    @FXML
    private void onActualiser() {
        chargerStatistiques();
    }

    private void chargerStatistiques() {
        String critere = cbCritere.getValue();
        if (critere == null) return;

        String expression = EXPRESSIONS.get(critere);
        String sql = "SELECT " + expression + " AS categorie, COUNT(*) AS effectif "
                + "FROM personne GROUP BY " + expression + " ORDER BY categorie";

        Map<String, Integer> resultats = new LinkedHashMap<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                resultats.put(rs.getString("categorie"), rs.getInt("effectif"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR,
                    "Erreur lors du chargement des statistiques : " + e.getMessage());
            alert.showAndWait();
            return;
        }

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Effectif par " + critere);

        for (Map.Entry<String, Integer> entry : resultats.entrySet()) {
            serie.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().clear();
        barChart.getData().add(serie);
    }
}
