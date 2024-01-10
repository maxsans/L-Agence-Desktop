package com.eseo.lagence.lagence;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Locale;

public class TenantView {

    private VBox tenantBox;

    public TenantView() {
        this.tenantBox = createBox();
    }

    private VBox createTitle() {
        Label titleLabel = new Label("Gestion des locataires : ");
        titleLabel.setFont(Font.font("Consolas", 36));

        VBox titleBox = new VBox(titleLabel);
        titleBox.setPadding(new Insets(0,0,0,20));
        titleLabel.setAlignment(Pos.CENTER_LEFT);

        return titleBox;
    }

    private VBox createTable() {
        TableView<Accommodation> tableAccommodation = new TableView<>();
        tableAccommodation.setMinWidth(1402);
        tableAccommodation.setMaxWidth(1402);

        // Create columns
        TableColumn<Accommodation, Integer> colId = new TableColumn<>("ID");
        TableColumn<Accommodation, String> colTenant = new TableColumn<>("Locataire");
        TableColumn<Accommodation, Double> colAccommodation = new TableColumn<>("Logement");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTenant.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Accommodation, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Accommodation, String> param) {
                UserAccount tenant = param.getValue().getTenant();
                return new SimpleStringProperty(tenant.getLastName().toUpperCase() + " " + tenant.getFirstName().substring(0, 1).toUpperCase() + tenant.getFirstName().substring(1).toLowerCase());
            }
        });
        colAccommodation.setCellValueFactory(new PropertyValueFactory<>("name"));

        colId.setMinWidth(60.00);
        colId.setMaxWidth(60.00);
        colId.setStyle("-fx-alignment: CENTER;");
        colTenant.setMinWidth(660.00);
        colTenant.setMaxWidth(660.00);
        colTenant.setStyle("-fx-alignment: CENTER;");
        colAccommodation.setMinWidth(660.00);
        colAccommodation.setMaxWidth(660.00);
        colAccommodation.setStyle("-fx-alignment: CENTER;");

        tableAccommodation.getColumns().addAll(colId, colTenant, colAccommodation);

        ObservableList<Accommodation> data = FXCollections.observableArrayList(
                new Accommodation(1, "Appartement Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36, true, new UserAccount(1, "test@gmail.com", "iSAAc", "Mauxion")),
                new Accommodation(2, "Appartement Doutre", 100.58, "bla bla bla", "2 rue TB", 3 , 84, true, new UserAccount(2, "test2@gmail.com", "test2", "T")),
                new Accommodation(3, "Appartement Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36, true, new UserAccount(1, "test@gmail.com", "test", "T")),
                new Accommodation(4, "Appartement Doutre", 100.58, "bla bla bla", "2 rue TB", 3 , 84, true, new UserAccount(2, "test2@gmail.com", "test2", "T")),
                new Accommodation(5, "Appartement Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36, true, new UserAccount(1, "test@gmail.com", "test", "T")),
                new Accommodation(6, "Appartement Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36, true, new UserAccount(1, "test@gmail.com", "test", "T"))
        );

        tableAccommodation.setItems(data);

        tableAccommodation.setRowFactory(tv -> {
            TableRow<Accommodation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    Accommodation selectedAccommodation = tableAccommodation.getSelectionModel().getSelectedItem();
                    if (selectedAccommodation != null) {
                        ModalTenant modalTenant = new ModalTenant();
                        modalTenant.openModal(selectedAccommodation);
                    }
                }
            });
            return row;
        });

        tableAccommodation.setPrefHeight(tableAccommodation.getItems().size() * 35 + 30);
        tableAccommodation.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox tabBox = new VBox(tableAccommodation);
        tabBox.setAlignment(Pos.CENTER);
        return tabBox;
    }

    public VBox createBox() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints column0 = new ColumnConstraints();
        column0.setHalignment(HPos.LEFT);
        column0.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().add(column0);

        RowConstraints row1 = new RowConstraints();
        row1.setValignment(VPos.CENTER);
        row1.setVgrow(Priority.ALWAYS);
        gridPane.getRowConstraints().add(row1);

        gridPane.add(createTitle(), 0, 0, 1, 1);
        gridPane.setPadding(new Insets(20));

        gridPane.add(createTable(), 0,1,1,1);

        VBox vBox = new VBox(gridPane);

        return vBox;
    }
}

