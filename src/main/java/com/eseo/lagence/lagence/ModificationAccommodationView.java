package com.eseo.lagence.lagence;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class ModificationAccommodationView {
    private TextField nameField;
    private TextField priceField;
    private TextField locationField;
    private Button buttonSave;

    public Button getButtonSave() {
        return buttonSave;
    }

    public ModificationAccommodationView(){
    }

    public VBox createBox () {
        Accommodation accommodation = new Accommodation("1", "Apparteent Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36);

                // Créer un champ de texte pour le nom
        nameField = new TextField();
        nameField.setText(accommodation.getName());
        nameField.setPromptText("Nom");

        // Créer un champ de texte pour le prix
        priceField = new TextField();
        priceField.setText(String.valueOf(accommodation.getPrice()));
        priceField.setPromptText("Prix");

        // Créer un champ de texte pour la localisation
        locationField = new TextField();
        locationField.setText(accommodation.getAddress());
        locationField.setPromptText("Localisation");

        // Créer un bouton pour valider les modifications
        buttonSave = new Button("Valider");
        buttonSave.setOnAction(event -> {
            // Mise à jour des données de l'hébergement
            accommodation.setName(nameField.getText());
            accommodation.setPrice(Double.valueOf(priceField.getText()));
            accommodation.setAddress(locationField.getText());
            StageManager.getInstance().setView(StageManager.SceneView.ACCOMMODATION_SCENE);
        });

        VBox vbox = new VBox(nameField,priceField,locationField,buttonSave);
        vbox.setSpacing(10);

        return vbox;
    }
}
