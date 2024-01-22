package com.eseo.lagence.lagence;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.function.BiConsumer;

public class MailBoxView {

    private Button acceptButton;
    private final Button viewButton = new Button("view"); //make the button unique for the button event listener;
    private Button declineButton;

    public Button getAcceptButton() {
        return acceptButton;
    }

    public Button getDeclineButton() {
        return declineButton;
    }

    public Button getViewButton() {
        return viewButton;
    }

    public MailBoxView() {
    }

    private Button createButton(FontAwesomeIcon icon, String color) {
        FontAwesomeIconView iconView = new FontAwesomeIconView(icon);
        iconView.setSize("1.5em");
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

    private TableCell createDeclineCell() {
        declineButton = createButton(FontAwesomeIcon.CLOSE, "#1c1c1e");

        return new TableCell<RequestAccommodation, RequestAccommodation>() {
            private final Button declineBtn = createButton(FontAwesomeIcon.CLOSE, "#1c1c1e");

            {
                declineBtn.setOnAction(event -> {
                    RequestAccommodation requestAccommodation = getTableView().getItems().get(getIndex());
                    if (StageManager.getInstance().showAlert("Êtes-vous sûr de supprimer la demande?")) {
                        System.out.println("OK");
                        String endpoint = "/properties/apply/" + requestAccommodation.getId()+"/"+"refused";
                        RequestService.getInstance().sendHttpRequest(endpoint, RequestService.HttpMethod.POST);
                        StageManager.getInstance().setView(StageManager.SceneView.MAILBOX_SCENE);
                    } else {
                        System.out.println("Cancel");
                    }
                });
            }

            @Override
            protected void updateItem(RequestAccommodation item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    declineBtn.setUserData(item);
                    setGraphic(declineBtn);
                } else {
                    setGraphic(null);
                }
            }
        };
    }

    private TableCell createAcceptCell() {
        acceptButton = createButton(FontAwesomeIcon.CHECK, "#ffa920");

        return new TableCell<RequestAccommodation, RequestAccommodation>() {
            private final Button acceptBtn = createButton(FontAwesomeIcon.CHECK, "#ffa920");

            {
                acceptBtn.setOnAction(event -> {
                    RequestAccommodation requestAccommodation = getTableView().getItems().get(getIndex());
                    if (StageManager.getInstance().showAlert("Êtes-vous sûr d'accepter la demande?")) {
                        System.out.println("OK");
                        String endpoint = "/properties/apply/" + requestAccommodation.getId()+"/"+"accepted";
                        RequestService.getInstance().sendHttpRequest(endpoint, RequestService.HttpMethod.POST);
                        StageManager.getInstance().setView(StageManager.SceneView.MAILBOX_SCENE);
                    } else {
                        System.out.println("Cancel");
                    }
                });
            }

            @Override
            protected void updateItem(RequestAccommodation item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    acceptBtn.setUserData(item);
                    setGraphic(acceptBtn);
                } else {
                    setGraphic(null);
                }
            }
        };
    }


    private VBox createTitle() {
        Label titleLabel = new Label("Demandes de logement : ");
        titleLabel.setFont(Font.font("Consolas", 36));

        VBox titleBox = new VBox(titleLabel);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setPadding(new Insets(20, 0, 0, 0));
        return titleBox;
    }


    private VBox createTable(ObservableList<RequestAccommodation> requestsAccommodations) {
        TableView<RequestAccommodation> tableRequests = new TableView<>();
        tableRequests.setMinWidth(1402);
        tableRequests.setMaxWidth(1402);

        // Create columns
        TableColumn<RequestAccommodation, String> colFirstName = new TableColumn<>("Prenom");
        TableColumn<RequestAccommodation, String> colName = new TableColumn<>("Nom");
        TableColumn<RequestAccommodation, String> colAccommodation = new TableColumn<>("Logement");
        TableColumn<RequestAccommodation, RequestAccommodation> colAccept = new TableColumn<>("");
        TableColumn<RequestAccommodation, RequestAccommodation> colDecline = new TableColumn<>("");

        colFirstName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getFirstName()));
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getLastName()));
        colAccommodation.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccommodation().getName()));
        colAccept.setCellFactory(param -> createAcceptCell());
        colDecline.setCellFactory(param -> createDeclineCell());

        colFirstName.setMinWidth(250.00);
        colFirstName.setMaxWidth(250.00);
        colFirstName.setStyle("-fx-alignment: CENTER;");
        colName.setMinWidth(250.00);
        colName.setMaxWidth(250.00);
        colName.setStyle("-fx-alignment: CENTER;");
        colAccommodation.setMinWidth(805.00);
        colAccommodation.setMaxWidth(805.00);
        colAccommodation.setStyle("-fx-alignment: CENTER;");
        colAccept.setStyle("-fx-alignment: CENTER;");
        colAccept.setMinWidth(40.00);
        colAccept.setMaxWidth(40.00);
        colDecline.setStyle("-fx-alignment: CENTER;");
        colDecline.setMinWidth(40.00);
        colDecline.setMaxWidth(40.00);

        tableRequests.getColumns().addAll(colFirstName, colName, colAccommodation, colAccept, colDecline);


        tableRequests.setItems(requestsAccommodations);

        tableRequests.setRowFactory(tv -> {
            TableRow<RequestAccommodation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    RequestAccommodation selectedRequest = row.getItem();
                    System.out.println("OK");
                    StageManager.getInstance().showRequestDetailModal(selectedRequest);
                }
            });
            return row;
        });
        tableRequests.setPrefHeight(tableRequests.getItems().size() * 35 + 30);
        tableRequests.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableRequests.setStyle("-fx-background-color: #fff5e0;");
        VBox tabBox = new VBox(tableRequests);
        tabBox.setAlignment(Pos.CENTER);
        return tabBox;
    }

    public VBox createBox() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Ligne 0
        RowConstraints row0 = new RowConstraints();
        row0.setValignment(VPos.TOP);
        gridPane.getRowConstraints().add(row0);

        // Ligne 1
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(80);
        row1.setVgrow(Priority.ALWAYS);
        gridPane.getRowConstraints().add(row1);

        ObservableList<RequestAccommodation> requestsAccommodations = RequestService.getInstance().getApply();

        // Ligne 0
        gridPane.add(createTitle(), 0, 0, 1, 1);

        // Ligne 1
        gridPane.add(createTable(requestsAccommodations), 0, 1, 1, 1);

        VBox vBox = new VBox(gridPane);

        return vBox;
    }

    private void setButtonClickHandler(Button button, Integer id, BiConsumer<Button, Integer> buttonClickHandler) {
        button.setOnAction(event -> {
            buttonClickHandler.accept(button, id);
        });
    }


}
