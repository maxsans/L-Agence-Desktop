package com.eseo.lagence.lagence;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.function.BiConsumer;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

public class UsersView {


    private Button deleteButton;

    public Button getDeleteButton() {
        return deleteButton;
    }

    public UsersView(){
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

        return new TableCell<UserAccount, UserAccount>() {
            private final Button delButton = createDeleteButton();

            {
                delButton.setOnAction(event -> {
                    UserAccount user = getTableView().getItems().get(getIndex());
                    if (StageManager.getInstance().showAlert("Êtes-vous sûr de supprimer cet utilisateur?")) {
                        System.out.println("OK");
                        String endpoint = "/user/" + user.getId();
                        RequestService.getInstance().sendHttpRequest(endpoint, RequestService.HttpMethod.DELETE);
                        StageManager.getInstance().setView(StageManager.SceneView.USERS_SCENE);
                    } else {
                        System.out.println("Cancel");
                    }
                });
            }
            @Override
            protected void updateItem(UserAccount item, boolean empty) {
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


    private VBox createTitle() {
        Label titleLabel = new Label("Liste des utilisateur : ");
        titleLabel.setFont(Font.font("Consolas", 36));

        VBox titleBox = new VBox(titleLabel);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setPadding(new Insets(20,0,0,0));
        return titleBox;
    }


    private VBox createTable(ObservableList<UserAccount> users) {
        TableView<UserAccount> tableRequests = new TableView<>();
        tableRequests.setMinWidth(1402);
        tableRequests.setMaxWidth(1402);

        // Create columns
        TableColumn<UserAccount, String> colFirstName = new TableColumn<>("Prenom");
        TableColumn<UserAccount, String> colName = new TableColumn<>("Nom");
        TableColumn<UserAccount, String> colMail = new TableColumn<>("Email");
        TableColumn<UserAccount, UserAccount> colDelete = new TableColumn<>("");


        colFirstName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        colMail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        colDelete.setCellFactory(param -> createDeleteCell());


        colFirstName.setMinWidth(400.00);
        colFirstName.setMaxWidth(400.00);
        colFirstName.setStyle("-fx-alignment: CENTER;");
        colName.setMinWidth(400.00);
        colName.setMaxWidth(400.00);
        colName.setStyle("-fx-alignment: CENTER;");
        colMail.setMinWidth(545.00);
        colMail.setMaxWidth(545.00);
        colMail.setStyle("-fx-alignment: CENTER;");
        colDelete.setStyle("-fx-alignment: CENTER;");
        colDelete.setMinWidth(40.00);
        colDelete.setMaxWidth(40.00);

        tableRequests.getColumns().addAll(colFirstName, colName, colMail, colDelete);

        ObservableList<UserAccount> data = users;

        tableRequests.setItems(data);

        /*tableRequests.setRowFactory(tv -> {
            TableRow<Request> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    Integer index = row.getItem().getId();
                    buttonClickHandler.accept(viewButton, index);
                }
            });
            return row;
        });*/
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
        ObservableList<UserAccount> users = RequestService.getInstance().getUsers();

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
        gridPane.add(createTable(users), 0, 1, 1, 1);

        VBox vBox = new VBox(gridPane);

        return vBox;
    }

    private void setButtonClickHandler(Button button, Integer id, BiConsumer<Button, Integer> buttonClickHandler) {
        button.setOnAction(event -> {
            buttonClickHandler.accept(button, id);
        });
    }


}
