package com.eseo.lagence.lagence.views;

import com.eseo.lagence.lagence.models.Properties;
import com.eseo.lagence.lagence.services.AccommodationService;
import com.eseo.lagence.lagence.utils.StageManager;
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
    private Properties currentAccommodation;
    private final TextField nameField = new TextField();
    private final TextField descriptionField = new TextField();
    private final TextField priceField = new TextField();
    private final TextField chargesPriceField = new TextField();
    private final TextField surfaceField = new TextField();
    private final TextField roomsCountField = new TextField();
    private final TextField addressField = new TextField();

    private List<File> images = new ArrayList<>();
    private ComboBox<String> typeComboBox;

    private Label errorMsg;

    private Button buttonSave;

    private String errorMessage;

    private static final Map<String, String> typeMap = new HashMap<>();

    static {
        typeMap.put("apartment", "Appartement");
        typeMap.put("house", "Maison");
    }


    public AccommodationModalView() {
    }

    public enum Mode {
        CREATE,
        UPDATE
    }

    public VBox createBox(Properties accommodation, Mode mode) {
        currentAccommodation = accommodation;
        // Accommodation accommodation = new Accommodation("1", "Apparteent Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36);

        Label title = new Label(mode == Mode.CREATE ? "Ajout d'un bien" : "Modification d'un bien");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");


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
        Label typeLabel = new Label("Type");
        VBox.setMargin(typeLabel, new Insets(0, 0, 2, 0));
        VBox typeBox = new VBox(typeLabel, typeComboBox);

        VBox fileBox = new VBox();
        if (mode == Mode.CREATE) {
            // Create components
            Label filePathField = new Label();
            filePathField.setStyle("-fx-text-fill: #858585;");
            Button browseButton = new Button("Sélectionner des images");

            // Configure file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.webp")
            );

            // Set action for the browse button
            browseButton.setOnAction(e -> {
                images = fileChooser.showOpenMultipleDialog(StageManager.getInstance().getStage());
                if (images != null) {
                    StringBuilder fileNames = new StringBuilder();
                    for (File selectedFile : images) {
                        fileNames.append(selectedFile.getAbsolutePath()).append("\n");
                    }
                    filePathField.setText(fileNames.toString());
                }
            });
            fileBox = new VBox(new Label("Images du bien"), browseButton, filePathField);
        }

        // Créer un bouton pour valider les modifications
        buttonSave = new Button(mode == Mode.CREATE ? "Créer" : "Mettre à jour");
        buttonSave.setOnAction(event -> onValidate(mode));
        buttonSave.setMaxWidth(Double.MAX_VALUE);

        errorMsg = new Label();
        errorMsg.setStyle("-fx-text-fill: #eb4949");

        VBox vbox = new VBox(title, nameBox, descriptionBox, priceBox, chargesPriceBox, surfaceBox,
                roomsCountBox, addressBox, typeBox, errorMsg, buttonSave);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20));
        vbox.setMinWidth(500);

        if (mode == Mode.CREATE) {
            int indexToInsertFileBox = Math.max(vbox.getChildren().size() - 2, 0);

            vbox.getChildren().add(indexToInsertFileBox, fileBox);
        }

        return vbox;
    }

    private void onValidate(Mode mode) {
        try {
            Properties property = validateAndCreatePropertiesEntity();

            if (mode == Mode.UPDATE) {
                AccommodationService.updateProperty(property);
            } else {
                AccommodationService.createProperty(property, images);
            }
            StageManager.getInstance().setView(StageManager.SceneView.ACCOMMODATION_SCENE);
            StageManager.getInstance().closeModalScene();

        } catch (ValidationException e) {
            // Handle validation error
            errorMsg.setText(e.getMessage());
        }
    }

    // Validate fields
    private Properties validateAndCreatePropertiesEntity() throws ValidationException {
        Properties newProperty = new Properties();
        newProperty.setId(currentAccommodation.getId());

        // Validate and set name
        String name = nameField.getText();
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Le nom ne peut pas être vide");
        }
        newProperty.setName(name);

        // Validate and set description
        String description = descriptionField.getText();
        if (description == null || description.trim().isEmpty()) {
            throw new ValidationException("La description ne peut pas être vide");
        }
        newProperty.setDescription(description);

        // Validate and set price
        try {
            double price = Double.parseDouble(priceField.getText());
            newProperty.setPrice(price);
        } catch (NumberFormatException e) {
            throw new ValidationException("Format de prix invalide");
        }

        // Validate and set chargesPrice
        try {
            int chargesPrice = Integer.parseInt(chargesPriceField.getText());
            newProperty.setChargesPrice(chargesPrice);
        } catch (NumberFormatException e) {
            throw new ValidationException("Format de charges invalide");
        }

        // Validate and set surface
        try {
            int surface = Integer.parseInt(surfaceField.getText());
            newProperty.setSurface(surface);
        } catch (NumberFormatException e) {
            throw new ValidationException("Format de surface invalide");
        }

        // Validate and set roomsCount
        try {
            int roomsCount = Integer.parseInt(roomsCountField.getText());
            newProperty.setRoomsCount(roomsCount);
        } catch (NumberFormatException e) {
            throw new ValidationException("Format de nombre de pièces invalide");
        }

        // Validate and set address
        String address = addressField.getText();
        if (address == null || address.trim().isEmpty()) {
            throw new ValidationException("L'adresse ne peut pas être vide");
        }
        newProperty.setAddress(address);

        // Reverse map to get the key from the selected value in the ComboBox
        String selectedType = getKeyByValue(typeMap, typeComboBox.getValue());
        if (selectedType == null) {
            throw new ValidationException("Type de logement invalide");
        }
        newProperty.setType(selectedType);

        return newProperty;
    }

    private VBox createFieldBox(String labelText, Object initialValue, TextField field) {
        Label label = new Label(labelText);
        VBox.setMargin(label, new Insets(0, 0, 2, 0));

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

// Custom exception class for validation errors
class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}