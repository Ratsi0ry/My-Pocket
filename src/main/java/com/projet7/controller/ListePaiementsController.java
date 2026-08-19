package com.projet7.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import com.projet7.db.DatabaseConnection;
import com.projet7.model.Paiement;

public class ListePaiementsController {

    @FXML private DatePicker dpDateDebut;
    @FXML private DatePicker dpDateFin;
    @FXML private Button btnFiltrer;
    @FXML private Button btnReinitialiser;
    @FXML private Button btnNouveauPaiement;
    @FXML private Button btnGenererRecu;

    @FXML private TableView<Paiement> tablePaiements;
    @FXML private TableColumn<Paiement, String> colIM;
    @FXML private TableColumn<Paiement, String> colNomPrenoms;
    @FXML private TableColumn<Paiement, String> colNumTarif;
    @FXML private TableColumn<Paiement, Number> colMontant;
    @FXML private TableColumn<Paiement, String> colDate;

    @FXML private Label lblTotal;

    private final ObservableList<Paiement> listePaiements = FXCollections.observableArrayList();
    private static final DateTimeFormatter FORMAT_AFFICHAGE = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {
        chargerPaiements(null, null);
    }

    private void chargerPaiements(LocalDate debut, LocalDate fin) {
        listePaiements.clear();

        StringBuilder sql = new StringBuilder(
                "SELECT pay.im, per.nom || ' ' || per.prenoms AS nomprenoms, pay.num_tarif, t.montant, pay.date "
                + "FROM payer pay "
                + "JOIN personne per ON pay.im = per.im "
                + "JOIN tarif t ON pay.num_tarif = t.num_tarif "
                + "WHERE 1=1");

        if (debut != null) sql.append(" AND pay.date >= ?");
        if (fin != null) sql.append(" AND pay.date <= ?");
        sql.append(" ORDER BY pay.date DESC");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (debut != null) ps.setDate(index++, Date.valueOf(debut));
            if (fin != null) ps.setDate(index++, Date.valueOf(fin));

            int totalMontant = 0;
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int montant = rs.getInt("montant");
                    totalMontant += montant;
                    listePaiements.add(new Paiement(
                            rs.getString("im"),
                            rs.getString("nomprenoms"),
                            rs.getString("num_tarif"),
                            montant,
                            rs.getDate("date").toLocalDate().format(FORMAT_AFFICHAGE)
                    ));
                }
            }

            lblTotal.setText("Nombre de paiements : " + listePaiements.size()
                    + "    |    Montant total : " + totalMontant + " Ar");

        } catch (SQLException e) {
            e.printStackTrace();
            afficherErreur("Erreur lors du chargement des paiements : " + e.getMessage());
        }

        tablePaiements.setItems(listePaiements);
    }

    @FXML
    private void onFiltrer() {
        chargerPaiements(dpDateDebut.getValue(), dpDateFin.getValue());
    }

    @FXML
    private void onReinitialiser() {
        dpDateDebut.setValue(null);
        dpDateFin.setValue(null);
        chargerPaiements(null, null);
    }

    @FXML
    private void onNouveauPaiement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormPaiement.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Nouveau paiement");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            chargerPaiements(dpDateDebut.getValue(), dpDateFin.getValue());
        } catch (IOException e) {
            e.printStackTrace();
            afficherErreur("Impossible d'ouvrir le formulaire : " + e.getMessage());
        }
    }

    @FXML
    private void onGenererRecu() {
        Paiement selection = tablePaiements.getSelectionModel().getSelectedItem();
        if (selection == null) {
            afficherErreur("Sélectionne un paiement dans la liste pour générer son reçu.");
            return;
        }
        genererRecuPdf(selection);
    }

    private void genererRecuPdf(Paiement paiement) {
        try {
            LocalDate date = LocalDate.parse(paiement.getDate(), FORMAT_AFFICHAGE);
            String mois = date.getMonth().getDisplayName(TextStyle.FULL, Locale.FRENCH);

            File dossier = new File("recus");
            if (!dossier.exists()) dossier.mkdirs();
            File fichier = new File(dossier, "recu_" + paiement.getIm() + "_" + date + ".pdf");

            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream content = new PDPageContentStream(document, page)) {
                    PDType1Font fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
                    PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

                    float y = 750;
                    content.beginText();
                    content.setFont(fontBold, 16);
                    content.newLineAtOffset(50, y);
                    content.showText("Reçu de paiement de pension");
                    content.endText();

                    y -= 40;
                    String[] lignes = {
                            "IM : " + paiement.getIm(),
                            "Nom et Prénoms : " + paiement.getNomPrenoms(),
                            "Mois : " + mois,
                            "Année : " + date.getYear(),
                            "Montant : " + paiement.getMontant() + " Ar"
                    };

                    for (String ligne : lignes) {
                        content.beginText();
                        content.setFont(font, 12);
                        content.newLineAtOffset(50, y);
                        content.showText(ligne);
                        content.endText();
                        y -= 25;
                    }
                }

                document.save(fichier);
            }

            Alert alert = new Alert(AlertType.INFORMATION, "Reçu généré : " + fichier.getAbsolutePath());
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            afficherErreur("Erreur lors de la génération du reçu : " + e.getMessage());
        }
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(AlertType.ERROR, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
