package com.projet7.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.projet7.db.DatabaseConnection;
import com.projet7.model.Personne;

public class ListePensionnairesController {

    @FXML private TextField txtRecherche;
    @FXML private Button btnRechercher;
    @FXML private Button btnReinitialiser;
    @FXML private ComboBox<String> cbStatut;
    @FXML private Button btnAjouter;
    @FXML private Button btnActualiser;

    @FXML private TableView<Personne> tablePersonnes;
    @FXML private TableColumn<Personne, String> colIM;
    @FXML private TableColumn<Personne, String> colNom;
    @FXML private TableColumn<Personne, String> colPrenoms;
    @FXML private TableColumn<Personne, String> colDateNais;
    @FXML private TableColumn<Personne, String> colDiplome;
    @FXML private TableColumn<Personne, String> colContact;
    @FXML private TableColumn<Personne, String> colStatut;
    @FXML private TableColumn<Personne, String> colSituation;
    @FXML private TableColumn<Personne, String> colNomConjoint;
    @FXML private TableColumn<Personne, String> colPrenomConjoint;
    @FXML private TableColumn<Personne, Void> colActions;

    @FXML private Label lblEffectifTotal;
    @FXML private Label lblVivants;
    @FXML private Label lblDecedes;

    private final ObservableList<Personne> listePersonnes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cbStatut.setItems(FXCollections.observableArrayList("Tous", "Vivant", "Décédé"));
        cbStatut.getSelectionModel().selectFirst();

        ajouterColonneActions();
        chargerPersonnes(null, null);
    }

    private void chargerPersonnes(String recherche, String statutFiltre) {
        listePersonnes.clear();

        StringBuilder sql = new StringBuilder(
                "SELECT im, nom, prenoms, datenais, diplome, contact, statut, situation, nomconjoint, prenomconjoint "
                + "FROM personne WHERE 1=1");

        if (recherche != null && !recherche.isBlank()) {
            sql.append(" AND (nom ILIKE ? OR im ILIKE ?)");
        }
        if (statutFiltre != null && !statutFiltre.equals("Tous")) {
            sql.append(" AND statut = ?");
        }
        sql.append(" ORDER BY nom");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (recherche != null && !recherche.isBlank()) {
                ps.setString(index++, "%" + recherche + "%");
                ps.setString(index++, "%" + recherche + "%");
            }
            if (statutFiltre != null && !statutFiltre.equals("Tous")) {
                ps.setBoolean(index++, statutFiltre.equals("Vivant"));
            }

            int vivants = 0;
            int decedes = 0;

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    boolean statutVivant = rs.getBoolean("statut");
                    Personne p = new Personne(
                            rs.getString("im"),
                            rs.getString("nom"),
                            rs.getString("prenoms"),
                            rs.getDate("datenais") != null ? rs.getDate("datenais").toLocalDate() : null,
                            rs.getString("diplome"),
                            rs.getString("contact"),
                            statutVivant,
                            rs.getString("situation"),
                            rs.getString("nomconjoint"),
                            rs.getString("prenomconjoint")
                    );
                    listePersonnes.add(p);
                    if (statutVivant) vivants++; else decedes++;
                }
            }

            lblEffectifTotal.setText("Effectif total : " + listePersonnes.size());
            lblVivants.setText("Vivants : " + vivants);
            lblDecedes.setText("Décédés : " + decedes);

        } catch (SQLException e) {
            e.printStackTrace();
            afficherErreur("Erreur lors du chargement des pensionnaires : " + e.getMessage());
        }

        tablePersonnes.setItems(listePersonnes);
    }

    @FXML
    private void onRechercher() {
        chargerPersonnes(txtRecherche.getText(), cbStatut.getValue());
    }

    @FXML
    private void onReinitialiser() {
        txtRecherche.clear();
        cbStatut.getSelectionModel().selectFirst();
        chargerPersonnes(null, null);
    }

    @FXML
    private void onFiltrerStatut() {
        chargerPersonnes(txtRecherche.getText(), cbStatut.getValue());
    }

    @FXML
    private void onActualiser() {
        chargerPersonnes(null, null);
    }

    @FXML
    private void onAjouter() {
        ouvrirFormulaire(null);
    }

    private Button createIconButton(String imageName, String tooltipText) {
        Button button = new Button();
        ImageView imageView = new ImageView(new Image(
                ListePensionnairesController.class.getResource("/Image/" + imageName).toExternalForm()));
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        button.setGraphic(imageView);
        button.setTooltip(new Tooltip(tooltipText));
        return button;
    }

    private void ajouterColonneActions() {
        colActions.setCellFactory(col -> new TableCell<>() {
            private final Button btnModifier = createIconButton("Modifier-50.png", "Modifier");
            private final Button btnSupprimer = createIconButton("supprimer-50.png", "Supprimer");
            private final Button btnDeces = createIconButton("pensionnaire-32.png", "Déclarer le décès");
            private final HBox box = new HBox(5, btnModifier, btnSupprimer, btnDeces);

            {
                btnModifier.setOnAction(e -> ouvrirFormulaire(getTableView().getItems().get(getIndex())));
                btnSupprimer.setOnAction(e -> supprimerPersonne(getTableView().getItems().get(getIndex())));
                btnDeces.setOnAction(e -> declarerDeces(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Personne p = getTableView().getItems().get(getIndex());
                    btnDeces.setVisible(p.isStatutVivant());
                    btnDeces.setManaged(p.isStatutVivant());
                    setGraphic(box);
                }
            }
        });
    }

    private void ouvrirFormulaire(Personne personne) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormPersonne.fxml"));
            Parent root = loader.load();

            FormPersonneController controller = loader.getController();
            controller.setPersonne(personne);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(personne == null ? "Ajouter un pensionnaire" : "Modifier un pensionnaire");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            chargerPersonnes(txtRecherche.getText(), cbStatut.getValue());
        } catch (IOException e) {
            e.printStackTrace();
            afficherErreur("Impossible d'ouvrir le formulaire : " + e.getMessage());
        }
    }

    private void declarerDeces(Personne p) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DeclarerDeces.fxml"));
            Parent root = loader.load();

            DeclarerDecesController controller = loader.getController();
            controller.setPersonne(p);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Déclaration de décès");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            chargerPersonnes(txtRecherche.getText(), cbStatut.getValue());
        } catch (IOException e) {
            e.printStackTrace();
            afficherErreur("Impossible d'ouvrir la déclaration de décès : " + e.getMessage());
        }
    }

    private void supprimerPersonne(Personne p) {
        Alert confirmation = new Alert(AlertType.CONFIRMATION,
                "Supprimer le pensionnaire " + p.getNom() + " " + p.getPrenoms() + " ?");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String sql = "DELETE FROM personne WHERE im = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, p.getIm());
                ps.executeUpdate();
                chargerPersonnes(txtRecherche.getText(), cbStatut.getValue());
            } catch (SQLException e) {
                e.printStackTrace();
                afficherErreur("Erreur lors de la suppression : " + e.getMessage());
            }
        }
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(AlertType.ERROR, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
