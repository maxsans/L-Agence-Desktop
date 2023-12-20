package com.eseo.lagence.lagence;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AccommodationModificationWindow {

    private Stage stage;
    private TextField nameField;
    private TextField priceField;
    private TextField locationField;

    public AccommodationModificationWindow(Accommodation accommodation) {
        stage = new Stage();

        // Créer un layout HBox pour placer les champs de texte
        HBox hbox = new HBox();
        hbox.setSpacing(10);

        // Créer un champ de texte pour le nom
        nameField = new TextField();
        nameField.setText(accommodation.getName());
        nameField.setPromptText("Nom");
        hbox.getChildren().add(nameField);

        // Créer un champ de texte pour le prix
        priceField = new TextField();
        priceField.setText(String.valueOf(accommodation.getPrice()));
        priceField.setPromptText("Prix");
        hbox.getChildren().add(priceField);

        // Créer un champ de texte pour la localisation
        locationField = new TextField();
        locationField.setText(accommodation.getLocation());
        locationField.setPromptText("Localisation");
        hbox.getChildren().add(locationField);

        // Créer un bouton pour valider les modifications
        Button buttonSave = new Button("Valider");
        buttonSave.setOnAction(event -> {
            // Mise à jour des données de l'hébergement
            accommodation.setName(nameField.getText());
            accommodation.setPrice(Double.valueOf(priceField.getText()));
            accommodation.setLocation(locationField.getText());

            // Fermeture de la fenêtre de modification
            stage.close();
        });
        hbox.getChildren().add(buttonSave);

        // Créer une scène et ajouter l'HBox
        Scene scene = new Scene(hbox);
        stage.setScene(scene);
        stage.setTitle("Modification d'hébergement");
        stage.show();
    }
}
