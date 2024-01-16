package com.eseo.lagence.lagence;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.function.BiConsumer;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

public class MailBoxView {


    private BiConsumer<Button, Integer> buttonClickHandler;

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

    public MailBoxView(BiConsumer<Button, Integer> buttonClickHandler){
        this.buttonClickHandler = buttonClickHandler;
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

    private TableCell createDeclineCell(){
        declineButton = createButton(FontAwesomeIcon.CLOSE, "#1c1c1e");

        return new TableCell<Request, Request>() {
            private final Button declineBtn = createButton(FontAwesomeIcon.CLOSE, "#1c1c1e");

            {
                declineBtn.setOnAction(event -> {
                    Request accommodation = getTableView().getItems().get(getIndex());
                    buttonClickHandler.accept(declineButton, accommodation.getId());
                });
            }
            @Override
            protected void updateItem(Request item, boolean empty) {
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

    private TableCell createAcceptCell(){
        acceptButton = createButton(FontAwesomeIcon.CHECK, "#ffa920");

        return new TableCell<Request, Request>() {
            private final Button acceptBtn = createButton(FontAwesomeIcon.CHECK, "#ffa920");

            {
                acceptBtn.setOnAction(event -> {
                    Request accommodation = getTableView().getItems().get(getIndex());
                    buttonClickHandler.accept(acceptButton, accommodation.getId());
                });
            }
            @Override
            protected void updateItem(Request item, boolean empty) {
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
        titleLabel.setPadding(new Insets(20,0,0,0));
        return titleBox;
    }


    private VBox createTable() {
        TableView<Request> tableRequests = new TableView<>();
        tableRequests.setMinWidth(1402);
        tableRequests.setMaxWidth(1402);

        // Create columns
        TableColumn<Request, String> colFirstName = new TableColumn<>("Prenom");
        TableColumn<Request, String> colName = new TableColumn<>("Nom");
        TableColumn<Request, String> colAccommodation = new TableColumn<>("Logement");
        TableColumn<Request, Request> colAccept = new TableColumn<>("");
        TableColumn<Request, Request> colDecline = new TableColumn<>("");

        colFirstName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getFirstName()));
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getLastName()));
        colAccommodation.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccommodation().getAddress()));
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

        ObservableList<Request> data = FXCollections.observableArrayList(
                new Request(1, 1, "test@gmail.com", "test", "T", 1, "e", 1.01, "t", "r", 2, 22),
                new Request(2, 1, "test@gmail.com", "test", "T", 1, "e", 1.01, "t", "r", 2, 22),
                new Request(3, 1, "test@gmail.com", "test", "T", 1, "e", 1.01, "t", "r", 2, 22),
                new Request(4, 1, "test@gmail.com", "test", "T", 1, "e", 1.01, "t", "r", 2, 22),
                new Request(5, 1, "test@gmail.com", "test", "T", 1, "e", 1.01, "t", "r", 2, 22),
                new Request(6, 1, "test@gmail.com", "test", "T", 1, "e", 1.01, "t", "r", 2, 22)
                );

        tableRequests.setItems(data);

        tableRequests.setRowFactory(tv -> {
            TableRow<Request> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    Integer index = row.getItem().getId();
                    buttonClickHandler.accept(viewButton, index);
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

    public VBox createBox(){
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


        // Ligne 0
        gridPane.add(createTitle(), 0, 0, 1, 1);

        // Ligne 1
        gridPane.add(createTable(), 0, 1, 1, 1);

        VBox vBox = new VBox(gridPane);

        return vBox;
    }

    private void setButtonClickHandler(Button button, Integer id, BiConsumer<Button, Integer> buttonClickHandler) {
        button.setOnAction(event -> {
            buttonClickHandler.accept(button, id);
        });
    }


}
