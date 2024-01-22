package com.eseo.lagence.lagence.views;

import com.eseo.lagence.lagence.utils.StageManager;
import com.eseo.lagence.lagence.models.Accommodation;
import com.eseo.lagence.lagence.models.RequestAccommodation;
import com.eseo.lagence.lagence.services.RequestService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Optional;


public class RequestDetailView {

    private Button createButton(FontAwesomeIcon icon, String color) {
        FontAwesomeIconView iconView = new FontAwesomeIconView(icon);
        iconView.setSize("4em");
        iconView.setFill(Paint.valueOf(color));

        Button button = new Button();
        button.setGraphic(iconView);
        button.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent; " +
                        "-fx-text-fill: black; " +
                        "-fx-font-weight: normal;");

        button.setOnMouseEntered(e -> {
            button.setScaleX(1.2);
            button.setScaleY(1.2);
        });
        button.setOnMouseExited(e -> {
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });
        return button;
    }


    public GridPane createBox(RequestAccommodation requestAccommodation) {
        Text titleCol1 = new Text("Demandeur");
        titleCol1.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
        titleCol1.setTextAlignment(TextAlignment.CENTER);

        Text titleCol2 = new Text("Logement");
        titleCol2.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
        titleCol2.setTextAlignment(TextAlignment.CENTER);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        ColumnConstraints col1 = new ColumnConstraints(500);
        col1.setHalignment(HPos.CENTER);
        ColumnConstraints col2 = new ColumnConstraints(500);
        col2.setHalignment(HPos.CENTER);
        grid.getColumnConstraints().addAll(col1, col2);

        grid.add(titleCol1, 0, 0);
        grid.add(titleCol2, 1, 0);

        grid.add(addUserData(requestAccommodation), 0, 1);
        grid.add(addAccommodationData(requestAccommodation.getAccommodation()), 1, 1);

        Button declineBtn = createButton(FontAwesomeIcon.CLOSE, "#1c1c1e");
        declineBtn.setAlignment(Pos.CENTER);
        declineBtn.setTranslateX(170);
        declineBtn.setOnAction(event -> {
            if (StageManager.getInstance().showAlert("Êtes-vous sûr d'accepter la demande?")) {
                System.out.println("OK");
                StageManager.getInstance().deleteModalScene();
                String endpoint = "/properties/apply/" + requestAccommodation.getId()+"/"+"refused";
                RequestService.getInstance().sendHttpRequest(endpoint, RequestService.HttpMethod.POST,Optional.empty());
                StageManager.getInstance().setView(StageManager.SceneView.MAILBOX_SCENE);
            } else {
                System.out.println("Cancel");
            }
        });

        Button acceptBtn = createButton(FontAwesomeIcon.CHECK, "#ffa920");
        acceptBtn.setAlignment(Pos.CENTER);
        acceptBtn.setTranslateX(-170);
        acceptBtn.setOnAction(event -> {
            if (StageManager.getInstance().showAlert("Êtes-vous sûr d'accepter la demande?")) {
                System.out.println("OK");
                StageManager.getInstance().deleteModalScene();
                String endpoint = "/properties/apply/" + requestAccommodation.getId()+"/"+"accepted";
                RequestService.getInstance().sendHttpRequest(endpoint, RequestService.HttpMethod.POST, Optional.empty());
                StageManager.getInstance().setView(StageManager.SceneView.MAILBOX_SCENE);
            } else {
                System.out.println("Cancel");
            }
        });


        grid.add(declineBtn, 0, 2);
        grid.add(acceptBtn, 1, 2);



        // Center text in each cell
        for (int row = 0; row < grid.getRowCount(); row++) {
            for (int col = 0; col < grid.getColumnCount(); col++) {
                if (grid.getChildren().get(row * grid.getColumnCount() + col) instanceof Text) {
                    Text text = (Text) grid.getChildren().get(row * grid.getColumnCount() + col);
                    GridPane.setHalignment(text, HPos.CENTER);
                }
            }
        }

        // Add padding
        grid.setPadding(new Insets(10));

        return grid;
    }

    private Text createText(String text, int wrappingWidth, TextAlignment alignment, boolean isBold) {
        Text label = new Text(text);
        label.setWrappingWidth(wrappingWidth);
        label.setTextAlignment(alignment);
        if (isBold) {
            label.setStyle("-fx-font-weight: bold;");
        }
        return label;
    }

    private VBox addUserData(RequestAccommodation requestAccommodation) {
        Text titleNameLabel = createText("Nom:", 100, TextAlignment.LEFT, false);
        Text nameLabel = createText(requestAccommodation.getUser().getLastName(), 400, TextAlignment.LEFT, true);

        Text titleFirstNameLabel = createText("Prénom:", 100, TextAlignment.LEFT, false);
        Text firstNameLabel = createText(requestAccommodation.getUser().getFirstName(), 400, TextAlignment.LEFT, true);

        Text titleEmailLabel = createText("Email:", 100, TextAlignment.LEFT, false);
        Text emailLabel = createText(requestAccommodation.getUser().getEmail(), 400, TextAlignment.LEFT, true);

        Text titleDescriptionLabel = createText("À propos :", 100, TextAlignment.LEFT, false);
        Text descriptionLabel = createText(requestAccommodation.getMotivationText(), 400, TextAlignment.LEFT, true);

        GridPane gridUser = new GridPane();
        gridUser.setHgap(10);
        gridUser.setVgap(10);
        ColumnConstraints col1 = new ColumnConstraints(100);
        ColumnConstraints col2 = new ColumnConstraints(400);
        gridUser.getColumnConstraints().addAll(col1, col2);

        gridUser.setAlignment(Pos.CENTER);
        gridUser.setPadding(new Insets(10));
        gridUser.add(titleNameLabel, 0, 0);
        gridUser.add(nameLabel, 1, 0);
        gridUser.add(titleFirstNameLabel, 0, 1);
        gridUser.add(firstNameLabel, 1, 1);
        gridUser.add(titleEmailLabel, 0, 2);
        gridUser.add(emailLabel, 1, 2);
        gridUser.add(titleDescriptionLabel, 0, 3);
        gridUser.add(descriptionLabel, 1, 3);

        Button downloadButton1 = createDownloadButton(requestAccommodation.getIdCardPath(), "Piece d'identité");
        Button downloadButton2 = createDownloadButton(requestAccommodation.getProofOfAddressPath(), "Justificatif de domicile");
        downloadButton1.setPadding(new Insets(10));
        downloadButton2.setPadding(new Insets(10));
        HBox hBox = new HBox(downloadButton1, downloadButton2);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(100);
        VBox vBox = new VBox(gridUser, hBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        return vBox;
    }

    private VBox addAccommodationData(Accommodation accommodation) {
        Text titleNameLabel = createText("Nom de l'annonce:", 100, TextAlignment.LEFT, false);
        Text nameLabel = createText(accommodation.getName(), 400, TextAlignment.LEFT, true);

        Text titlePriceLabel = createText("Prix:", 100, TextAlignment.LEFT, false);
        Text priceLabel = createText(accommodation.getPrice().toString() + "€", 400, TextAlignment.LEFT, true);

        Text titleDescriptionLabel = createText("Description:", 100, TextAlignment.LEFT, false);
        Text descriptionLabel = createText(accommodation.getDescription(), 400, TextAlignment.LEFT, true);

        Text titleLocationLabel = createText("Localisation:", 100, TextAlignment.LEFT, false);
        Text locationLabel = createText(accommodation.getAddress(), 400, TextAlignment.LEFT, true);

        Text titleSizeLabel = createText("Taille du logement:", 100, TextAlignment.LEFT, false);
        Text sizeLabel = createText(accommodation.getSurface().toString() + " m²", 400, TextAlignment.LEFT, true);

        Text titleRoomsCountLabel = createText("Nombre de pièces:", 100, TextAlignment.LEFT, false);
        Text roomsCountLabel = createText(accommodation.getRoomsCount().toString(), 400, TextAlignment.LEFT, true);

        GridPane gridAccommodation = new GridPane();
        gridAccommodation.setHgap(10);
        gridAccommodation.setVgap(10);
        ColumnConstraints col1 = new ColumnConstraints(100);
        ColumnConstraints col2 = new ColumnConstraints(400);
        gridAccommodation.getColumnConstraints().addAll(col1, col2);

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

        VBox vBox = new VBox(gridAccommodation);
        vBox.setAlignment(Pos.CENTER);
        return vBox;    }

    private static Button createDownloadButton(String dossier, String title) {
        Button downloadButton = new Button(title);
        downloadButton.setOnAction(event -> {
            String dossierPath = dossier.replace('\\', '/');
            RequestService.getInstance().downloadPicture(dossierPath);
        });
        return downloadButton;
    }
}
