package com.eseo.lagence.lagence;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalTenant {
    private Stage modalStage;

    public void openModal(Rental selectedRental) {
        modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setResizable(false);

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        Label infoLabel = new Label(selectedRental.getUser().getLastName() + " " + selectedRental.getUser().getFirstName());

        vbox.getChildren().add(infoLabel);
        Scene modalScene = new Scene(vbox, 1000,500);

        modalStage.setScene(modalScene);
        modalStage.setTitle("Fiche locataire");

        modalStage.show();
    }

<<<<<<< Updated upstream
=======
    private VBox createRoot(Rental selectedRental) {
        VBox root = new VBox(10);

        HBox tenantAndAccommodationBox = new HBox(10);
        tenantAndAccommodationBox.getChildren().addAll(
                createTenantBox(selectedRental),
                createAccommodationBox(selectedRental)
        );

        HBox buttonBox = createButtonBox(selectedRental);

        root.getChildren().addAll(tenantAndAccommodationBox, buttonBox);

        return new VBox(root);
    }

    private VBox createTenantBox(Rental selectedRental) {
        VBox boxTenant = new VBox(10);
        boxTenant.setPrefSize(500, 400);
        boxTenant.setStyle("-fx-alignment: TOP_CENTER;");
        boxTenant.setPadding(new Insets(20, 0, 0, 0));

        Label titleLabel = createLabel("Locataire", 30);
        HBox nameBox = createTextWithLabel("Nom : ", selectedRental.getUser().getLastName().toUpperCase() +
                " " + capitalizeFirstLetter(selectedRental.getUser().getFirstName()));
        HBox mailBox = createTextWithLabel("Mail : ", selectedRental.getUser().getEmail());

        titleLabel.setAlignment(Pos.CENTER);
        nameBox.setAlignment(Pos.CENTER);
        mailBox.setAlignment(Pos.CENTER);

        boxTenant.getChildren().addAll(titleLabel, nameBox, mailBox);

        return boxTenant;
    }

    private VBox createAccommodationBox(Rental selectedRental) {
        VBox boxAccommodation = new VBox(10);
        boxAccommodation.setPrefSize(500, 400);
        boxAccommodation.setStyle("-fx-alignment: TOP_CENTER;");
        boxAccommodation.setPadding(new Insets(20, 0, 0, 0));

        Accommodation accommodation = selectedRental.getAccommodation();

        Label titleLabel = createLabel("Logement", 30);
        HBox nameBox = createTextWithLabel("Nom : ", accommodation.getName());
        HBox locationBox = createTextWithLabel("Localisation : ", accommodation.getAddress());
        HBox priceBox = createTextWithLabel("Prix : ", accommodation.getPrice() + "€");
        HBox roomsBox = createTextWithLabel("Nombre de pièces : ", String.valueOf(accommodation.getRoomsCount()));
        HBox sizeBox = createTextWithLabel("Taille : ", accommodation.getSurface() + "m²");

        titleLabel.setAlignment(Pos.CENTER);
        nameBox.setAlignment(Pos.CENTER);
        locationBox.setAlignment(Pos.CENTER);
        priceBox.setAlignment(Pos.CENTER);
        roomsBox.setAlignment(Pos.CENTER);
        sizeBox.setAlignment(Pos.CENTER);

        boxAccommodation.getChildren().addAll(titleLabel, nameBox, locationBox, priceBox, roomsBox, sizeBox);

        return boxAccommodation;
    }

    private HBox createButtonBox(Rental selectedRental) {
        HBox boxButton = new HBox();
        boxButton.setPrefSize(1000, 50);
        boxButton.setStyle("-fx-alignment: TOP_CENTER;");

        Button button = new Button("Supprimer le contrat");

        button.setOnAction(event -> {
            if (StageManager.getInstance().showAlert("Êtes-vous sûr de supprimer ce contrat?")) {
                System.out.println("OK");
                String endpoint = "/user/rental/admin" + selectedRental.getId() +"/" + selectedRental.getAccommodation().getId();
                RequestService.getInstance().sendHttpRequest(endpoint, RequestService.HttpMethod.DELETE);
                modalStage.close();
                StageManager.getInstance().setView(StageManager.SceneView.TENANT_SCENE);
            } else {
                System.out.println("Cancel");
            }
        });


        boxButton.getChildren().addAll(button);


        return boxButton;
    }

    private Label createLabel(String text, double fontSize) {
        Label label = new Label(text);
        label.setFont(Font.font("Consolas", fontSize));
        return label;
    }

    private HBox createTextWithLabel(String labelText, String value) {
        HBox hbox = new HBox();
        Label label = new Label();
        Text titleText = new Text(labelText);
        titleText.setStyle("-fx-font-weight: BOLD;");
        titleText.setFont(new Font(16));
        Text valueText = new Text(value);
        valueText.setFont(new Font(16));
        hbox.setPadding(new Insets(10, 0, 0, 0));
        hbox.getChildren().addAll(titleText, valueText);
        label.setGraphic(hbox);
        return hbox;
    }

    private String capitalizeFirstLetter(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

>>>>>>> Stashed changes
    public void close() {
        if (modalStage != null) {
            modalStage.close();
        }
    }
}

