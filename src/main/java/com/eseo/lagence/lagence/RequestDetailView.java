package com.eseo.lagence.lagence;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class RequestDetailView {

    public HBox createBox(RequestAccommodation requestAccommodation) {
        VBox userVBox = addUserData(requestAccommodation.getUser());
        VBox accommodationVBox = addAccommodationData(requestAccommodation.getAccommodation());
        HBox hBox = new HBox(userVBox,accommodationVBox);
        return hBox;
    }

    private VBox addUserData(UserAccount user) {
        Text title = new Text("Demandeur");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
        title.setTextAlignment(TextAlignment.LEFT);

        Text titleNameLabel = new Text("Nom:");
        titleNameLabel.setTextAlignment(TextAlignment.CENTER);
        Text nameLabel = new Text(user.getLastName());
        nameLabel.setStyle("-fx-font-weight: bold;");
        nameLabel.setTextAlignment(TextAlignment.CENTER);

        Text titleFirstNameLabel = new Text("Prénom:");
        titleFirstNameLabel.setTextAlignment(TextAlignment.CENTER);
        Text firstNameLabel = new Text(user.getFirstName());
        firstNameLabel.setStyle("-fx-font-weight: bold;");
        firstNameLabel.setTextAlignment(TextAlignment.CENTER);

        Text titleEmailLabel = new Text("Email:");
        titleEmailLabel.setTextAlignment(TextAlignment.CENTER);
        Text emailLabel = new Text(user.getEmail());
        emailLabel.setStyle("-fx-font-weight: bold;");
        emailLabel.setTextAlignment(TextAlignment.CENTER);

        GridPane gridUser = new GridPane();
        gridUser.setHgap(10);
        gridUser.setVgap(10);
        gridUser.setPadding(new Insets(10));
        gridUser.add(titleNameLabel, 0, 0);
        gridUser.add(nameLabel, 1, 0);
        gridUser.add(titleFirstNameLabel, 0, 1);
        gridUser.add(firstNameLabel, 1, 1);
        gridUser.add(titleEmailLabel, 0, 2);
        gridUser.add(emailLabel, 1, 2);

        Button downloadButton = createDownloadButton("requestAccommodation.getDossierPath()");
        downloadButton.setPadding(new Insets(10));
        VBox vBox = new VBox(title, gridUser, downloadButton);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private VBox addAccommodationData(Accommodation accommodation) {
        Text title = new Text("Logement");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        Text titleNameLabel = new Text("Nom de l'annonce:");
        titleNameLabel.setTextAlignment(TextAlignment.CENTER);
        Text nameLabel = new Text(accommodation.getName());
        nameLabel.setStyle("-fx-font-weight: bold;");
        nameLabel.setTextAlignment(TextAlignment.CENTER);

        Text titlePriceLabel = new Text("Prix:");
        titlePriceLabel.setTextAlignment(TextAlignment.CENTER);
        Text priceLabel = new Text(accommodation.getPrice().toString() + "€");
        priceLabel.setStyle("-fx-font-weight: bold;");
        priceLabel.setTextAlignment(TextAlignment.CENTER);

        Text titleDescriptionLabel = new Text("Description:");
        titleDescriptionLabel.setTextAlignment(TextAlignment.CENTER);
        Text descriptionLabel = new Text(accommodation.getDescription());
        descriptionLabel.setStyle("-fx-font-weight: bold;");
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);

        Text titleLocationLabel = new Text("Localisation:");
        titleLocationLabel.setTextAlignment(TextAlignment.CENTER);
        Text locationLabel = new Text(accommodation.getAddress());
        locationLabel.setStyle("-fx-font-weight: bold;");
        locationLabel.setTextAlignment(TextAlignment.CENTER);

        Text titleSizeLabel = new Text("Taille du logement:");
        titleSizeLabel.setTextAlignment(TextAlignment.CENTER);
        Text sizeLabel = new Text(accommodation.getSurface().toString() + " m²");
        sizeLabel.setStyle("-fx-font-weight: bold;");
        sizeLabel.setTextAlignment(TextAlignment.CENTER);

        Text titleRoomsCountLabel = new Text("Nombre de pièces:");
        titleRoomsCountLabel.setTextAlignment(TextAlignment.CENTER);
        Text roomsCountLabel = new Text(accommodation.getRoomsCount().toString());
        roomsCountLabel.setStyle("-fx-font-weight: bold;");
        roomsCountLabel.setTextAlignment(TextAlignment.CENTER);

        GridPane gridAccommodation = new GridPane();
        gridAccommodation.setHgap(10);
        gridAccommodation.setVgap(10);
        gridAccommodation.setPadding(new Insets(10));
        gridAccommodation.add(titleNameLabel, 0, 0);
        gridAccommodation.add(nameLabel, 1, 0);
        gridAccommodation.add(titlePriceLabel, 0,1);
        gridAccommodation.add(priceLabel, 1, 1);
        gridAccommodation.add(titleDescriptionLabel, 0,2);
        gridAccommodation.add(descriptionLabel, 1, 2);
        gridAccommodation.add(titleLocationLabel, 0,3);
        gridAccommodation.add(locationLabel, 1, 3);
        gridAccommodation.add(titleSizeLabel, 0,4);
        gridAccommodation.add(sizeLabel, 1, 4);
        gridAccommodation.add(titleRoomsCountLabel, 0,5);
        gridAccommodation.add(roomsCountLabel, 1, 5);

        VBox vBox = new VBox(title, gridAccommodation);
        vBox.setAlignment(Pos.CENTER);
        return vBox;    }

    private static Button createDownloadButton(String dossierPath) {
        Button downloadButton = new Button("Télécharger Dossier");
        downloadButton.setOnAction(event -> {
            System.out.println("Téléchargement du dossier: " + dossierPath);
        });

        return downloadButton;
    }
}
