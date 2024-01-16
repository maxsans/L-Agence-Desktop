package com.eseo.lagence.lagence;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

public class AccommodationView {

    private BiConsumer<Button, Integer> buttonClickHandler;

    private Button addButton;
    private Button modifyButton = new Button("modify"); //make the button unique for the button event listener
    private Button deleteButton;

    public Button getAddButton() {
        return addButton;
    }
    public Button getModifyButton() {
        return modifyButton;
    }
    public Button getDeleteButton() {
        return deleteButton;
    }


    public AccommodationView(BiConsumer<Button, Integer> buttonClickHandler){
        this.buttonClickHandler = buttonClickHandler;
    }
    private Button createDeleteButton() {
        FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
        iconView.setSize("1.5em");

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

    private TableCell createDeleteCell(){
        deleteButton = createDeleteButton(); //make the button unique for the button event listener

        return new TableCell<Accommodation, Accommodation>() {
            private final Button delButton = createDeleteButton();

            {
                delButton.setOnAction(event -> {
                    Accommodation accommodation = getTableView().getItems().get(getIndex());
                    buttonClickHandler.accept(deleteButton, accommodation.getId());
                });
            }
            @Override
            protected void updateItem(Accommodation item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    delButton.setUserData(item);
                    setGraphic(delButton);
                } else {
                    setGraphic(null);
                }
            }
        };
    }

    private TableCell createPriceCell(){
        return new TableCell<Accommodation, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f €", item)); // Formatting with the "€" symbol
                }
            }
        };
    }

    private VBox createTitle() {
        Label titleLabel = new Label("Liste des biens : ");
        titleLabel.setFont(Font.font("Consolas", 36));

        VBox titleBox = new VBox(titleLabel);
        titleLabel.setAlignment(Pos.CENTER);

        return titleBox;
    }

    private VBox createAddButton() {
        FontAwesomeIconView addIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
        addIcon.setSize("18");
        addIcon.setStyleClass("plus-icon");

        Label addButtonLabel = new Label("Ajouter", addIcon);
        addButtonLabel.setFont(Font.font("Consolas", 20));

        this.addButton = new Button();
        addButton.setGraphic(addButtonLabel);
        addButton.setOnAction(event -> {buttonClickHandler.accept(addButton, 1);});


        // Create a VBox to contain the button
        VBox addBox = new VBox(addButton);
        addBox.setAlignment(Pos.CENTER);

        return addBox;
    }



    private VBox createTable(ObservableList<Accommodation> data) {
        TableView<Accommodation> tableAccommodation = new TableView<>();
        tableAccommodation.setMinWidth(1402);
        tableAccommodation.setMaxWidth(1402);

        // Create columns
        TableColumn<Accommodation, Integer> colId = new TableColumn<>("ID");
        TableColumn<Accommodation, String> colName = new TableColumn<>("Nom");
        TableColumn<Accommodation, Double> colPrice = new TableColumn<>("Prix");
        TableColumn<Accommodation, String> colLocation = new TableColumn<>("Localisation");
        TableColumn<Accommodation, Accommodation> colDelete = new TableColumn<>("");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDelete.setCellFactory(param -> createDeleteCell());

        colPrice.setCellFactory(tc -> createPriceCell());

        colId.setMinWidth(60.00);
        colId.setMaxWidth(60.00);
        colId.setStyle("-fx-alignment: CENTER;");
        colName.setMinWidth(530.00);
        colName.setMaxWidth(530.00);
        colName.setStyle("-fx-alignment: CENTER;");
        colPrice.setMinWidth(100.00);
        colPrice.setMaxWidth(100.00);
        colPrice.setStyle("-fx-alignment: CENTER-RIGHT;");
        colLocation.setMinWidth(655.00);
        colLocation.setMaxWidth(655.00);
        colLocation.setStyle("-fx-alignment: CENTER;");
        colDelete.setStyle("-fx-alignment: CENTER;");
        colDelete.setMinWidth(40.00);
        colDelete.setMaxWidth(40.00);

        tableAccommodation.getColumns().addAll(colId, colName, colPrice, colLocation, colDelete);

        /*ObservableList<Accommodation> data = FXCollections.observableArrayList(
                new Accommodation(1, "Apparteent Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36),
                new Accommodation(2, "Apparteent Doutre", 50.00, "bla bla bla", "2 rue TB", 5 , 52),
                new Accommodation(3, "Apparteent Doutre", 100.58, "bla bla bla", "2 rue TB", 3 , 84),
                new Accommodation(1, "Apparteent Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36 ),
                new Accommodation(2, "Apparteent Doutre", 50.00, "bla bla bla", "2 rue TB", 5 , 52),
                new Accommodation(3, "Apparteent Doutre", 100.58, "bla bla bla", "2 rue TB", 3 , 84),
                new Accommodation(1, "Apparteent Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36),
                new Accommodation(2, "Apparteent Doutre", 50.00, "bla bla bla", "2 rue TB", 5 , 52),
                new Accommodation(1, "Apparteent Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36),
                new Accommodation(1, "Apparteent Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36),
                new Accommodation(2, "Apparteent Doutre", 50.00, "bla bla bla", "2 rue TB", 5 , 52),
                new Accommodation(3, "Apparteent Doutre", 100.58, "bla bla bla", "2 rue TB", 3 , 84),
                new Accommodation(1, "Apparteent Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36 ),
                new Accommodation(2, "Apparteent Doutre", 50.00, "bla bla bla", "2 rue TB", 5 , 52),
                new Accommodation(3, "Apparteent Doutre", 100.58, "bla bla bla", "2 rue TB", 3 , 84),
                new Accommodation(1, "Apparteent Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36),
                new Accommodation(2, "Apparteent Doutre", 50.00, "bla bla bla", "2 rue TB", 5 , 52),
                new Accommodation(1, "Apparteent Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36)
                );
*/
        tableAccommodation.setItems(data);

        tableAccommodation.setRowFactory(tv -> {
            TableRow<Accommodation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    Integer index = row.getItem().getId();
                    buttonClickHandler.accept(modifyButton, index);
                }
            });
            return row;
        });
        tableAccommodation.setPrefHeight(tableAccommodation.getItems().size() * 35 + 30);
        tableAccommodation.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableAccommodation.setStyle("-fx-background-color: #fff5e0;");
        VBox tabBox = new VBox(tableAccommodation);
        tabBox.setAlignment(Pos.CENTER);
        return tabBox;
    }

    public VBox createBox(RequestService requestService){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ObservableList<Accommodation> accommodations = requestService.getAccommodations();
        // Ligne 0
        RowConstraints row0 = new RowConstraints();
        row0.setValignment(VPos.TOP);
        gridPane.getRowConstraints().add(row0);

        // Ligne 1
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(80);
        row1.setVgrow(Priority.ALWAYS);
        gridPane.getRowConstraints().add(row1);

        // Ligne 2
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(10);
        row2.setValignment(VPos.BOTTOM);
        gridPane.getRowConstraints().add(row2);

        // Ligne 0
        gridPane.add(createTitle(), 0, 0, 1, 1);

        // Ligne 1
        gridPane.add(createTable(accommodations), 0, 1, 1, 1);

        // Ligne 2
        gridPane.add(createAddButton(), 0, 2, 1, 1);
        VBox vBox = new VBox(gridPane);

        return vBox;
    }

    private void setButtonClickHandler(Button button, Integer id, BiConsumer<Button, Integer> buttonClickHandler) {
        button.setOnAction(event -> {
            buttonClickHandler.accept(button, id);
        });
    }


}
