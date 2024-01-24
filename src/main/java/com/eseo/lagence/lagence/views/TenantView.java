package com.eseo.lagence.lagence.views;

import com.eseo.lagence.lagence.models.Properties;
import com.eseo.lagence.lagence.models.Rental;
import com.eseo.lagence.lagence.models.UserAccount;
import com.eseo.lagence.lagence.services.RentalService;
import com.eseo.lagence.lagence.services.RequestService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class TenantView {

    private VBox tenantBox;

    public TenantView() {
    }

    private VBox createTitle() {
        Label titleLabel = new Label("Gestion des locataires : ");
        titleLabel.setFont(Font.font("Consolas", 36));

        VBox titleBox = new VBox(titleLabel);
        titleBox.setPadding(new Insets(0, 0, 0, 20));
        titleLabel.setAlignment(Pos.CENTER_LEFT);

        return titleBox;
    }

    private VBox createTable() {
        TableView<Rental> tableRental = new TableView<>();
        tableRental.setMinWidth(1402);
        tableRental.setMaxWidth(1402);

        // Create columns
        TableColumn<Rental, String> colTenant = new TableColumn<>("Locataire");
        TableColumn<Rental, String> colAccommodation = new TableColumn<>("Logement");

        colTenant.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rental, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Rental, String> param) {
                UserAccount user = param.getValue().getUser();
                return new SimpleStringProperty(user.getLastName().toUpperCase() + " " + user.getFirstName().substring(0, 1).toUpperCase() + user.getFirstName().substring(1).toLowerCase());
            }
        });
        colAccommodation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rental, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Rental, String> param) {
                Properties accommodation = param.getValue().getAccommodation();
                return new SimpleStringProperty(accommodation.getName());
            }
        });

        colTenant.setMinWidth(690.00);
        colTenant.setMaxWidth(690.00);
        colTenant.setStyle("-fx-alignment: CENTER;");
        colAccommodation.setMinWidth(690.00);
        colAccommodation.setMaxWidth(690.00);
        colAccommodation.setStyle("-fx-alignment: CENTER;");

        tableRental.getColumns().addAll( colTenant, colAccommodation);

        ObservableList<Rental> data = RentalService.getTenants();

        tableRental.setItems(data);

        tableRental.setRowFactory(tv -> {
            TableRow<Rental> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    Rental selectedRental = tableRental.getSelectionModel().getSelectedItem();
                    if (selectedRental != null) {
                        ModalTenant modalTenant = new ModalTenant();
                        modalTenant.openModal(selectedRental);
                    }
                }
            });
            return row;
        });

        tableRental.setPrefHeight(tableRental.getItems().size() * 35 + 30);
        tableRental.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox tabBox = new VBox(tableRental);
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

        gridPane.add(createTable(), 0, 1, 1, 1);

        VBox vBox = new VBox(gridPane);

        return vBox;
    }
}

