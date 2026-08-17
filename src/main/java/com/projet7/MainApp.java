package com.projet7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(MainApp.class.getResource("/css/styles.css").toExternalForm());
        
        stage.setTitle("Gestion des Pensions");
        stage.setScene(scene);
        stage.setWidth(1000);
        stage.setHeight(650);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
