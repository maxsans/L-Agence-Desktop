package com.eseo.lagence.lagence;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    private Button listAccommodationsButton;
    private Button mailboxButton;
    private Button usersButton;
    private Button disconnectButton;

    private NavBarView navBarView;

    private AccommodationView accommodationView;

    private ModificationAccommodationView modificationAccommodationView;

    private Stage stage;


    @Override
    public void start(Stage stage) throws IOException {

        this.navBarView = new NavBarView(this::handleButtonClickNavBar);
        this.accommodationView = new AccommodationView(this::handleButtonClickTableAccommodation);
        this.modificationAccommodationView = new ModificationAccommodationView(this::handleButtonClickNavBar);


        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        VBox vBox = new VBox(navBarView.createNavBar(), accommodationView.createBox());
        Scene scene = new Scene(vBox);
        stage.setTitle("L'Agence");
        stage.setScene(scene);
        stage.setMinWidth(1450.00);
        stage.setMinHeight(1000.00);
        stage.setMaximized(true);


        Image icon = new Image(getClass().getResourceAsStream("/images/logo_Agence.png"));
        stage.getIcons().add(icon);

        stage.show();

        this.stage = stage;
    }


    private void handleButtonClickNavBar(Button button) {
        if (button == navBarView.getListAccommodationsButton()){
            VBox vBoxAccommodation = new VBox(navBarView.getNavBar(), accommodationView.createBox());
            Scene sceneAccommodation = new Scene(vBoxAccommodation);
            stage.setScene(sceneAccommodation);
        }
        else if (button == navBarView.getMailboxButton()){
            VBox vBoxModificationAccommodation = new VBox(navBarView.getNavBar(), modificationAccommodationView.createBox(new Accommodation(1, "Apparteent Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36, true, new UserAccount(1, "test@gmail.com", "test", "T"))));
            Scene sceneModificationAccommodation = new Scene(vBoxModificationAccommodation);
            stage.setScene(sceneModificationAccommodation);
        }
        else if (button == navBarView.getUsersButton()){
            System.out.println("usersButton Button Clicked");
        }
        else if (button == navBarView.getDisconnectButton()){
            System.out.println("disconnectButton Button Clicked");
        }
        else{
            // Handle other buttons if needed
        }
    }

    private void handleButtonClickTableAccommodation(Button button, Integer id) {
        if (button == accommodationView.getDeleteButton()){
            System.out.println("getDeleteButton : " + id);
        }
        else if (button == accommodationView.getAddButton()){
            System.out.println("Add Button Clicked");
        }
        else if (button == accommodationView.getModifyButton()){
            System.out.println("getModifyButton Button Clicked : " + id);
        }
        else{
            // Handle other buttons if needed
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
