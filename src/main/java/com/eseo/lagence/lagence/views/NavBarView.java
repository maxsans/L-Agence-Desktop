package com.eseo.lagence.lagence.views;

import com.eseo.lagence.lagence.utils.StageManager;
import com.eseo.lagence.lagence.services.RequestService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class NavBarView {

    private VBox navBar;
    private Button listAccommodationsButton;
    private Button bedButton;
    private Button mailboxButton;
    private Button usersButton;
    private Button disconnectButton;

    public NavBarView(){
    }

    public VBox getNavBar() {
        if (navBar != null){
            VBox navBarBox = new VBox(navBar);
            return navBarBox;
        }
        return new VBox();
    }


    public Button getListAccommodationsButton() {
        return listAccommodationsButton;
    }

    public Button getBedButton() {
        return bedButton;
    }

    public Button getMailboxButton() {
        return mailboxButton;
    }


    public Button getUsersButton() {
        return usersButton;
    }


    public Button getDisconnectButton() {
        return disconnectButton;
    }


    public VBox createNavBar(){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(30);


        Label titleLabel = new Label("L'Agence");
        titleLabel.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 36));
        titleLabel.setStyle("-fx-text-fill: #18181a;");
        Image iconLagence = new Image(getClass().getResourceAsStream("/images/logo_Agence.png"));
        ImageView logoLagence = new ImageView(iconLagence);

        logoLagence.setFitWidth(38);
        logoLagence.setFitHeight(38);

        HBox titleBox = new HBox(logoLagence, titleLabel);
        HBox.setMargin(titleLabel, new Insets(0, 0, 0, 15)); // Adjust the insets as needed

        listAccommodationsButton = new Button("");
        FontAwesomeIconView iconViewHome = new FontAwesomeIconView(FontAwesomeIcon.HOME);
        iconViewHome.setSize("3em");
        listAccommodationsButton.setGraphic(iconViewHome);
        listAccommodationsButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent; " +
                        "-fx-text-fill: black; " +
                        "-fx-font-weight: normal;");
        listAccommodationsButton.setOnMouseEntered(e -> {
            listAccommodationsButton.setScaleX(1.1);
            listAccommodationsButton.setScaleY(1.1);
        });
        listAccommodationsButton.setOnMouseExited(e -> {
            listAccommodationsButton.setScaleX(1.0);
            listAccommodationsButton.setScaleY(1.0);
        });

        bedButton = new Button("");
        FontAwesomeIconView iconViewBedBox = new FontAwesomeIconView(FontAwesomeIcon.BED);
        iconViewBedBox.setSize("3em");

        bedButton.setGraphic(iconViewBedBox);
        bedButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent; " +
                        "-fx-text-fill: black; " +
                        "-fx-font-weight: normal;");

        Tooltip tooltip = new Tooltip("Espace Locataires");

        Tooltip.install(bedButton, tooltip);

        bedButton.setOnMouseEntered(e -> {
            bedButton.setScaleX(1.1);
            bedButton.setScaleY(1.1);
        });
        bedButton.setOnMouseExited(e -> {
            bedButton.setScaleX(1.0);
            bedButton.setScaleY(1.0);
        });

        mailboxButton = new Button("");
        FontAwesomeIconView iconViewMailBox = new FontAwesomeIconView(FontAwesomeIcon.ENVELOPE);
        iconViewMailBox.setSize("3em");
        mailboxButton.setGraphic(iconViewMailBox);
        mailboxButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent; " +
                        "-fx-text-fill: black; " +
                        "-fx-font-weight: normal;");
        mailboxButton.setOnMouseEntered(e -> {
            mailboxButton.setScaleX(1.1);
            mailboxButton.setScaleY(1.1);
        });
        mailboxButton.setOnMouseExited(e -> {
            mailboxButton.setScaleX(1.0);
            mailboxButton.setScaleY(1.0);
        });

        usersButton = new Button("");
        FontAwesomeIconView iconViewUsers = new FontAwesomeIconView(FontAwesomeIcon.USERS);
        iconViewUsers.setSize("3em");
        usersButton.setGraphic(iconViewUsers);
        usersButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent; " +
                        "-fx-text-fill: black; " +
                        "-fx-font-weight: normal;");
        usersButton.setOnMouseEntered(e -> {
            usersButton.setScaleX(1.1);
            usersButton.setScaleY(1.1);
        });
        usersButton.setOnMouseExited(e -> {
            usersButton.setScaleX(1.0);
            usersButton.setScaleY(1.0);
        });

        disconnectButton = new Button("");
        FontAwesomeIconView iconViewDisconnect = new FontAwesomeIconView(FontAwesomeIcon.SIGN_OUT);
        iconViewDisconnect.setSize("3em");
        disconnectButton.setGraphic(iconViewDisconnect);
        disconnectButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent; " +
                        "-fx-text-fill: black; " +
                        "-fx-font-weight: normal;");
        disconnectButton.setOnMouseEntered(e -> {
            disconnectButton.setScaleX(1.1);
            disconnectButton.setScaleY(1.1);
        });
        disconnectButton.setOnMouseExited(e -> {
            disconnectButton.setScaleX(1.0);
            disconnectButton.setScaleY(1.0);
        });
        listAccommodationsButton.setOnAction(event -> {
            StageManager.getInstance().setView(StageManager.SceneView.ACCOMMODATION_SCENE);});
        bedButton.setOnAction(event -> {StageManager.getInstance().setView(StageManager.SceneView.TENANT_SCENE);});
        mailboxButton.setOnAction(event -> {StageManager.getInstance().setView(StageManager.SceneView.MAILBOX_SCENE);});
        usersButton.setOnAction(event -> {StageManager.getInstance().setView(StageManager.SceneView.USERS_SCENE);});
        disconnectButton.setOnAction(event -> {
            RequestService.getInstance().logout();
            StageManager.getInstance().setView(StageManager.SceneView.LOGIN_SCENE);
        });



        // Column 0
        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPercentWidth(75); // 70% of the space
        col0.setHalignment(HPos.LEFT);
        gridPane.getColumnConstraints().add(col0);


        // Column 0
        gridPane.add(titleBox, 0, 0, 1, 1);

        // Column 1
        gridPane.add(listAccommodationsButton, 1, 0, 1, 1);

        // Column 2
        gridPane.add(bedButton, 2, 0, 1, 1);

        // Column 3
        gridPane.add(mailboxButton, 3, 0, 1, 1);

        // Column 4
        gridPane.add(usersButton, 4, 0, 1, 1);

        // Column 5
        gridPane.add(disconnectButton, 5, 0, 1, 1);

        gridPane.setPadding(new Insets(20,0,20,0));

        gridPane.setStyle("-fx-background-color: #e0e0e0;");

        VBox navBarBox = new VBox(gridPane);
        this.navBar = navBarBox;

        return navBarBox;
    }

}
