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
        TableView<Rental> tableRental = new TableView<>();
        tableRental.setMinWidth(1402);
        tableRental.setMaxWidth(1402);

        // Create columns
        TableColumn<Rental, Integer> colId = new TableColumn<>("ID");
        TableColumn<Rental, String> colTenant = new TableColumn<>("Locataire");
        TableColumn<Rental, String> colAccommodation = new TableColumn<>("Logement");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
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
                Accommodation accommodation = param.getValue().getAccommodation();
                return new SimpleStringProperty(accommodation.getName());
            }
        });

        colId.setMinWidth(60.00);
        colId.setMaxWidth(60.00);
        colId.setStyle("-fx-alignment: CENTER;");
        colTenant.setMinWidth(660.00);
        colTenant.setMaxWidth(660.00);
        colTenant.setStyle("-fx-alignment: CENTER;");
        colAccommodation.setMinWidth(660.00);
        colAccommodation.setMaxWidth(660.00);
        colAccommodation.setStyle("-fx-alignment: CENTER;");

        tableRental.getColumns().addAll(colId, colTenant, colAccommodation);

        ObservableList<Rental> data = FXCollections.observableArrayList(
            new Rental(1,new Accommodation(1, "Appartement Doutre", 12.22, "Description aléatoire", "2 rue TB", 2, 36, true), new UserAccount(1, "test@gmail.com", "iSAAc", "Mauxion")),
            new Rental(2,new Accommodation(2, "Maison de Ville", 20.55, "Une belle maison avec jardin", "5 rue XYZ", 3, 60, false), new UserAccount(2, "john.doe@example.com", "JohnD", "Doe")),
            new Rental(3,new Accommodation(3, "Studio Lumineux", 8.75, "Studio moderne proche du centre-ville", "8 avenue ABC", 1, 25, true), new UserAccount(3, "jane.smith@example.com", "JaneS", "Smith")),
            new Rental(4,new Accommodation(4, "Loft Spacieux", 15.99, "Espace ouvert avec décoration moderne", "10 rue LMN", 2, 45, true), new UserAccount(4, "robert.jones@example.com", "RobJ", "Jones")),
            new Rental(5,new Accommodation(5, "Villa de Luxe", 35.75, "Villa avec piscine et vue sur la mer", "15 chemin OPQ", 5, 120, false), new UserAccount(5, "lisa.white@example.com", "LisaW", "White")),
            new Rental(6,new Accommodation(6, "Chambre Simple", 7.99, "Idéale pour les voyageurs solo", "3 rue UVW", 1, 18, true), new UserAccount(6, "samuel.brown@example.com", "SamB", "Brown")),
            new Rental(7,new Accommodation(7, "Duplex Moderne", 25.49, "Design contemporain sur deux niveaux", "20 avenue XYZ", 3, 70, true), new UserAccount(7, "emily.jones@example.com", "EmilyJ", "Jones")),
            new Rental(8,new Accommodation(8, "Penthouse Vue Mer", 45.99, "Vue panoramique depuis le dernier étage", "25 rue PQR", 4, 100, false), new UserAccount(8, "charlie.smith@example.com", "CharlieS", "Smith")),
            new Rental(9,new Accommodation(9, "Cabane en Forêt", 10.75, "Retraite paisible au cœur de la nature", "30 chemin DEF", 2, 40, true), new UserAccount(9, "olivia.white@example.com", "OliviaW", "White")),
            new Rental(10,new Accommodation(10, "Appartement de Charme", 18.88, "Ambiance chaleureuse et décoration élégante", "18 rue JKL", 2, 50, true), new UserAccount(10, "michael.brown@example.com", "MichaelB", "Brown")),
            new Rental(11,new Accommodation(11, "Chalet Rustique", 22.35, "Un refuge chaleureux avec cheminée", "22 avenue GHI", 3, 65, false), new UserAccount(11, "sophie.martin@example.com", "SophieM", "Martin")),
            new Rental(12,new Accommodation(12, "Maison de Campagne", 28.75, "Authenticité et tranquillité assurées", "35 chemin RST", 4, 80, true), new UserAccount(12, "david.white@example.com", "DavidW", "White")),
            new Rental(13,new Accommodation(13, "Studio Étudiant", 9.99, "Parfait pour les étudiants avec bureau inclus", "4 rue UVW", 1, 30, true), new UserAccount(13, "sara.jones@example.com", "SaraJ", "Jones")),
            new Rental(14,new Accommodation(14, "Manoir Historique", 55.25, "Un bijou architectural chargé d'histoire", "40 avenue MNO", 8, 200, false), new UserAccount(14, "adam.smith@example.com", "AdamS", "Smith")),
            new Rental(15,new Accommodation(15, "Auberge de Montagne", 17.88, "Ambiance conviviale et vue panoramique", "14 chemin UVW", 6, 120, true), new UserAccount(15, "mia.brown@example.com", "MiaB", "Brown")),
            new Rental(16,new Accommodation(16, "Bungalow Plage", 30.45, "Accès direct à la plage et coucher de soleil", "60 rue KLM", 4, 90, false), new UserAccount(16, "leo.martin@example.com", "LeoM", "Martin")),
            new Rental(17,new Accommodation(17, "Pied-à-Terre", 12.99, "Petit appartement au cœur de la ville", "6 avenue DEF", 1, 40, true), new UserAccount(17, "mia.jones@example.com", "MiaJ", "Jones")),
            new Rental(18,new Accommodation(18, "Chalet en Bois", 25.75, "Retraite confortable au milieu des arbres", "45 chemin GHI", 3, 60, true), new UserAccount(18, "daniel.white@example.com", "DanielW", "White")),
            new Rental(19,new Accommodation(19, "Suite Présidentielle", 75.99, "Luxe ultime avec service VIP", "80 rue NOP", 6, 180, false), new UserAccount(19, "lucas.brown@example.com", "LucasB", "Brown")),
            new Rental(20,new Accommodation(20, "Village Vacances", 40.88, "Diverses activités pour toute la famille", "10 avenue JKL", 10, 300, true), new UserAccount(20, "eva.martin@example.com", "EvaM", "Martin")),
            new Rental(21,new Accommodation(21, "Château Médiéval", 60.25, "Voyage dans le temps avec tout le confort moderne", "25 rue EFG", 12, 250, false), new UserAccount(21, "noah.smith@example.com", "NoahS", "Smith")),
            new Rental(22,new Accommodation(22, "Résidence Étudiante", 15.49, "Logements modernes proches des universités", "15 chemin XYZ", 5, 50, true), new UserAccount(22, "emma.white@example.com", "EmmaW", "White")),
            new Rental(23,new Accommodation(23, "Gîte Rural", 18.75, "Authenticité et calme à la campagne", "5 rue GHI", 4, 70, true), new UserAccount(23, "jayden.brown@example.com", "JaydenB", "Brown")),
            new Rental(24,new Accommodation(24, "Hôtel de Ville", 35.99, "Confort au cœur de la cité historique", "12 avenue NOP", 8, 120, false), new UserAccount(24, "olivia.jones@example.com", "OliviaJ", "Jones")),
            new Rental(25,new Accommodation(25, "Auberge de la Forêt", 20.88, "Séjour en pleine nature avec cuisine locale", "30 chemin PQR", 6, 80, true), new UserAccount(25, "ethan.white@example.com", "EthanW", "White")),
            new Rental(26,new Accommodation(26, "Maison d'Architecte", 45.75, "Design contemporain et matériaux écologiques", "8 rue KLM", 3, 100, true), new UserAccount(26, "mia.white@example.com", "MiaW", "White")),
            new Rental(27,new Accommodation(27, "Résidence Sénior", 28.99, "Confort adapté pour les seniors", "18 avenue DEF", 2, 60, false), new UserAccount(27, "jacob.jones@example.com", "JacobJ", "Jones")),
            new Rental(28,new Accommodation(28, "Hébergement Insolite", 12.75, "Nuitée inoubliable dans un lieu unique", "40 chemin GHI", 1, 25, true), new UserAccount(28, "ava.smith@example.com", "AvaS", "Smith")),
            new Rental(29,new Accommodation(29, "Péniche Aménagée", 22.45, "Séjour original sur l'eau", "14 rue UVW", 2, 50, true), new UserAccount(29, "aiden.brown@example.com", "AidenB", "Brown")),
            new Rental(30,new Accommodation(30, "Résidence d'Artistes", 18.88, "Espace créatif et inspirant", "22 avenue XYZ", 3, 40, true), new UserAccount(30, "mia.jones@example.com", "MiaJ", "Jones")),
            new Rental(31,new Accommodation(31, "Appartement Familial", 30.25, "Confort pour toute la famille", "10 rue NOP", 4, 80, false), new UserAccount(31, "ethan.brown@example.com", "EthanB", "Brown")),
            new Rental(32,new Accommodation(32, "Maison de Plage", 42.99, "Vue sur l'océan et accès direct à la plage", "20 chemin JKL", 6, 120, true), new UserAccount(32, "olivia.smith@example.com", "OliviaS", "Smith")),
            new Rental(33,new Accommodation(33, "Studio d'Artiste", 15.75, "Espace créatif avec lumière naturelle", "5 avenue GHI", 1, 30, true), new UserAccount(33, "jackson.white@example.com", "JacksonW", "White")),
            new Rental(34,new Accommodation(34, "Auberge de Montagne", 17.88, "Ambiance conviviale et vue panoramique", "14 chemin UVW", 6, 120, true), new UserAccount(34, "mia.brown@example.com", "MiaB", "Brown")),
            new Rental(35,new Accommodation(35, "Bungalow Plage", 30.45, "Accès direct à la plage et coucher de soleil", "60 rue KLM", 4, 90, false), new UserAccount(35, "leo.martin@example.com", "LeoM", "Martin")),
            new Rental(36,new Accommodation(36, "Pied-à-Terre", 12.99, "Petit appartement au cœur de la ville", "6 avenue DEF", 1, 40, true), new UserAccount(36, "mia.jones@example.com", "MiaJ", "Jones")),
            new Rental(37,new Accommodation(37, "Chalet en Bois", 25.75, "Retraite confortable au milieu des arbres", "45 chemin GHI", 3, 60, true), new UserAccount(37, "daniel.white@example.com", "DanielW", "White")),
            new Rental(38,new Accommodation(38, "Suite Présidentielle", 75.99, "Luxe ultime avec service VIP", "80 rue NOP", 6, 180, false), new UserAccount(38, "lucas.brown@example.com", "LucasB", "Brown")),
            new Rental(39,new Accommodation(39, "Village Vacances", 40.88, "Diverses activités pour toute la famille", "10 avenue JKL", 10, 300, true), new UserAccount(39, "eva.martin@example.com", "EvaM", "Martin")),
            new Rental(40,new Accommodation(40, "Château Médiéval", 60.25, "Voyage dans le temps avec tout le confort moderne", "25 rue EFG", 12, 250, false), new UserAccount(40, "noah.smith@example.com", "NoahS", "Smith")),
            new Rental(41,new Accommodation(41, "Résidence Étudiante", 15.49, "Logements modernes proches des universités", "15 chemin XYZ", 5, 50, true), new UserAccount(41, "emma.white@example.com", "EmmaW", "White")),
            new Rental(42,new Accommodation(42, "Gîte Rural", 18.75, "Authenticité et calme à la campagne", "5 rue GHI", 4, 70, true), new UserAccount(42, "jayden.brown@example.com", "JaydenB", "Brown")),
            new Rental(43,new Accommodation(43, "Hôtel de Ville", 35.99, "Confort au cœur de la cité historique", "12 avenue NOP", 8, 120, false), new UserAccount(43, "olivia.jones@example.com", "OliviaJ", "Jones")),
            new Rental(44,new Accommodation(44, "Auberge de la Forêt", 20.88, "Séjour en pleine nature avec cuisine locale", "30 chemin PQR", 6, 80, true), new UserAccount(44, "ethan.white@example.com", "EthanW", "White")),
            new Rental(45,new Accommodation(45, "Maison d'Architecte", 45.75, "Design contemporain et matériaux écologiques", "8 rue KLM", 3, 100, true), new UserAccount(45, "mia.white@example.com", "MiaW", "White")),
            new Rental(46,new Accommodation(46, "Résidence Sénior", 28.99, "Confort adapté pour les seniors", "18 avenue DEF", 2, 60, false), new UserAccount(46, "jacob.jones@example.com", "JacobJ", "Jones"))
        );

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

        gridPane.add(createTable(), 0,1,1,1);

        VBox vBox = new VBox(gridPane);

        return vBox;
    }
}

