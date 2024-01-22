package com.eseo.lagence.lagence.views;

import com.eseo.lagence.lagence.utils.StageManager;
import com.eseo.lagence.lagence.models.Accommodation;
import com.eseo.lagence.lagence.services.RequestService;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccommodationModalView {
    private Accommodation accommodation;
    private final TextField nameField = new TextField();
    private final TextField descriptionField = new TextField();
    private final TextField priceField = new TextField();
    private final TextField chargesPriceField = new TextField();
    private final TextField surfaceField = new TextField();
    private final TextField roomsCountField = new TextField();
    private final TextField addressField = new TextField();

    private List<File> images = new ArrayList<>();
    private ComboBox<String> typeComboBox;

    private Button buttonSave;

    private static final Map<String, String> typeMap = new HashMap<>();

    static {
        typeMap.put("apartment", "Appartement");
        typeMap.put("house", "Maison");
    }

    public Button getButtonSave() {
        return buttonSave;
    }

    public AccommodationModalView() {
    }

    public enum Mode {
        CREATE,
        UPDATE
    }

    public VBox createBox(Accommodation accommodation, Mode mode) {
        // Accommodation accommodation = new Accommodation("1", "Apparteent Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36);

        // Create VBox for the name property
        VBox nameBox = createFieldBox("Nom", accommodation.getName(), nameField);

        // Create VBox for the description property
        VBox descriptionBox = createFieldBox("Description du bien", accommodation.getDescription(), descriptionField);

        // Create VBox for the price property
        VBox priceBox = createFieldBox("Prix", accommodation.getPrice(), priceField);

        // Create VBox for the chargesPrice property
        VBox chargesPriceBox = createFieldBox("Charges", accommodation.getChargesPrice(), chargesPriceField);

        // Create VBox for the surface property
        VBox surfaceBox = createFieldBox("Surface", accommodation.getSurface(), surfaceField);

        // Create VBox for the roomsCount property
        VBox roomsCountBox = createFieldBox("Nombre de pièces", accommodation.getRoomsCount(), roomsCountField);

        // Create VBox for the address property
        VBox addressBox = createFieldBox("Adresse", accommodation.getAddress(), addressField);

        // Create ComboBox for the type property
        typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll(typeMap.values());
        typeComboBox.setPromptText("Type de bien");
        typeComboBox.setValue(typeMap.get(accommodation.getType()));
        VBox typeBox = new VBox(new Label("Type"), typeComboBox);

        VBox fileBox = new VBox();
        if (mode == Mode.CREATE) {
            // Create components
            TextField filePathField = new TextField();
            Button browseButton = new Button("Browse");

            // Configure file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );

            // Set action for the browse button
            browseButton.setOnAction(e -> {
                images = fileChooser.showOpenMultipleDialog(StageManager.getInstance().getStage());
                if (images != null) {
                    // Assuming filePathField is a TextField
                    StringBuilder fileNames = new StringBuilder();
                    for (File selectedFile : images) {
                        fileNames.append(selectedFile.getAbsolutePath()).append("\n");
                    }
                    filePathField.setText(fileNames.toString());
                }
            });
            fileBox = new VBox(new Label("Images du bien"), filePathField, browseButton);
        }

        // Créer un bouton pour valider les modifications
        buttonSave = new Button("Valider");
        buttonSave.setOnAction(event -> {
            Accommodation accomo = new Accommodation();
            accomo.setId(accommodation.getId());

            accomo.setName(nameField.getText());
            accomo.setDescription(descriptionField.getText());
            accomo.setPrice(Double.valueOf(priceField.getText()));
            accomo.setChargesPrice(Integer.valueOf(chargesPriceField.getText()));
            accomo.setSurface(Integer.valueOf(surfaceField.getText()));
            accomo.setRoomsCount(Integer.valueOf(roomsCountField.getText()));
            accomo.setAddress(addressField.getText());

            // Reverse map to get the key from the selected value in the ComboBox
            String selectedType = getKeyByValue(typeMap, typeComboBox.getValue());
            accomo.setType(selectedType);

            if (mode == Mode.UPDATE) {
                RequestService.getInstance().updateProperty(accomo);
            } else {
                RequestService.getInstance().updateProperty(accomo);
            }

            StageManager.getInstance().setView(StageManager.SceneView.ACCOMMODATION_SCENE);
            StageManager.getInstance().closeModalScene();
        });

        VBox vbox = new VBox(nameBox, descriptionBox, priceBox, chargesPriceBox, surfaceBox,
                roomsCountBox, addressBox, typeBox, fileBox, buttonSave);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20));
        vbox.setMinWidth(500);

        return vbox;
    }

    private VBox createFieldBox(String labelText, Object initialValue, TextField field) {
        Label label = new Label(labelText);

        field.setPromptText(labelText);
        if (initialValue != null) {
            String stringValue = convertToString(initialValue);
            field.setText(stringValue);
        } else {
            field.setText("");
        }

        return new VBox(label, field);
    }

    private String convertToString(Object value) {
        // Handle conversion based on the type of the value
        if (value == null) {
            return "";
        } else if (value instanceof Double || value instanceof Integer) {
            return String.valueOf(value);
        } else {
            return value.toString();
        }
    }

    // Helper method to get a key from a value in a map
    private <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null; // If no key is found
    }
}
