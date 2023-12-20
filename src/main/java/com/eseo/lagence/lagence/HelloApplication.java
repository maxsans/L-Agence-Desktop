package com.eseo.lagence.lagence;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.IOException;
import javafx.scene.layout.VBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;


public class HelloApplication extends Application {

    private Button createModifyButton() {
        FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
        iconView.setSize("1.5em");

        Button button = new Button();
        button.setGraphic(iconView);

        return button;
    }

    private TableCell createModifyCell(){
        return new TableCell<Accommodation, Accommodation>() {
            final Button button = createModifyButton();


            {
                button.setOnAction(event -> {
                    Accommodation accommodation = getTableView().getItems().get(getIndex());
                    System.out.println("ID de la ligne : " + accommodation.getId());
                });
            }


            @Override
            protected void updateItem(Accommodation item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    button.setUserData(item);
                    setGraphic(button);
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
                    setText(String.format("%.2f €", item)); // Formatage avec le symbole "€"
                }
            }
        };
    }



    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        TableView<Accommodation> tableAccommodation = new TableView<Accommodation>();

        tableAccommodation.setMinWidth(801);
        tableAccommodation.setMaxWidth(801);





        Button buttonModify = new Button("Modifier");
        buttonModify.setText("Modifier");
        buttonModify.setAlignment(Pos.CENTER_RIGHT);
        buttonModify.setOnAction(event -> {
            Accommodation accommodation = tableAccommodation.getItems().get(tableAccommodation.getSelectionModel().getSelectedIndex());
            new AccommodationModificationWindow(accommodation);
        });



        // Création des colonnes
        TableColumn<Accommodation, Integer> colId = new TableColumn<>("ID");
        TableColumn<Accommodation, String> colName = new TableColumn<>("Nom");
        TableColumn<Accommodation, Double> colPrice = new TableColumn<>("Prix");
        TableColumn<Accommodation, String> colLocation = new TableColumn<>("Localisation");
        TableColumn<Accommodation, Accommodation> colModifier = new TableColumn<>("Modifier");

        //TableColumn<Accommodation, String> colTenant = new TableColumn<>("Locataire");


        // Associer les propriétés du modèle de données aux colonnes
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colModifier.setCellFactory(param -> createModifyCell());

        colPrice.setCellFactory(tc -> createPriceCell());

        //colTenant.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenant()));
        // colId.prefWidthProperty().bind(tableAccommodation.widthProperty().divide(10)); // w * 1/20
        colId.setMinWidth(40.00);
        colId.setMaxWidth(40.00);
        // colName.prefWidthProperty().bind(tableAccommodation.widthProperty().divide(5)); // w * 8/20
        colName.setMinWidth(300.00);
        colName.setMaxWidth(300.00);
        // colPrice.prefWidthProperty().bind(tableAccommodation.widthProperty().divide(10)); // w * 1/20
        colPrice.setMinWidth(60.00);
        colPrice.setMaxWidth(60.00);
        colPrice.setStyle("-fx-alignment: CENTER-RIGHT;");
        // colLocation.prefWidthProperty().bind(tableAccommodation.widthProperty().divide(2)); // w * 1/2
        colLocation.setMinWidth(330.00);
        colLocation.setMaxWidth(330.00);
        // colModifier.prefWidthProperty().bind(tableAccommodation.widthProperty().divide(10)); // w * 1/2
        colModifier.setStyle("-fx-alignment: CENTER;");
        colModifier.setMinWidth(70.00);
        colModifier.setMaxWidth(70.00);



        // Ajouter les colonnes à la TableView
        tableAccommodation.getColumns().addAll(colId, colName, colPrice, colLocation, colModifier);



        // Récupérer les données de l'API (remplacez cela par votre propre logique)
        // Les données fictives sont utilisées ici
        // Vous devrez peut-être utiliser une bibliothèque comme Jackson ou Gson pour traiter les données JSON de l'API.
        // Exemple de données fictives :
        ObservableList<Accommodation> data = FXCollections.observableArrayList(
                new Accommodation(1, "Apparteent Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36, true, new UserAccount(1, "test@gmail.com", "test", "T")),
                new Accommodation(2, "Apparteent Doutre", 50.00, "bla bla bla", "2 rue TB", 5 , 52, false, null),
                new Accommodation(3, "Apparteent Doutre", 100.58, "bla bla bla", "2 rue TB", 3 , 84, true, new UserAccount(2, "test2@gmail.com", "test2", "T"))
        );

        // Ajouter les données à la TableView
        tableAccommodation.setItems(data);

        // Création d'une mise en page VBox pour afficher la TableView
        VBox vbox = new VBox(tableAccommodation);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 800, 500);
        // scene.getStylesheets().add(getClass().getResource("/images/TableView.css").toExternalForm());

        stage.setTitle("L'Agence");
        stage.setScene(scene);
        stage.setMinWidth(800.00);
        // Image icon = new Image(getClass().getResourceAsStream("images/logo_Agence.png"));
        // stage.getIcons().add(icon);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}