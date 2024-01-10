package com.eseo.lagence.lagence;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {

    private NavBarView navBarView;

    private AccommodationView accommodationView;

    private MailBoxView mailBoxView;

    private ModificationAccommodationView modificationAccommodationView;

    private Stage stage;

    private Stage modalStage;



    @Override
    public void start(Stage stage) throws IOException {

        this.navBarView = new NavBarView(this::handleButtonClick);
        this.accommodationView = new AccommodationView(this::handleButtonClickTable);
        this.mailBoxView = new MailBoxView(this::handleButtonClickTable);
        this.modificationAccommodationView = new ModificationAccommodationView(this::handleButtonClick);

        this.modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Modal Window");

        VBox vBox = new VBox(navBarView.createNavBar(), accommodationView.createBox());
        Scene scene = new Scene(vBox);
        stage.setTitle("L'Agence");
        stage.setScene(scene);
        Image icon = new Image(getClass().getResourceAsStream("/images/logo_Agence.png"));
        stage.getIcons().add(icon);

        stage.show();

        stage.setMaximized(true);
        stage.setResizable(false);

        this.stage = stage;
    }


    private void handleButtonClick(Button button) {
        double currentWidth = stage.getScene().getWidth();
        double currentHeight = stage.getScene().getHeight();
        if (button == navBarView.getListAccommodationsButton()){
            VBox vBoxAccommodation = new VBox(navBarView.getNavBar(), accommodationView.createBox());
            Scene sceneAccommodation = new Scene(vBoxAccommodation, currentWidth, currentHeight);
            stage.setScene(sceneAccommodation);

        }
        else if (button == navBarView.getBedButton()){
            System.out.println("BedButton Button Clicked");
        }
        else if (button == navBarView.getMailboxButton()){
            System.out.println("Mailbox Button Clicked");
            VBox vBoxModificationAccommodation = new VBox(navBarView.getNavBar(), mailBoxView.createBox());
            Scene sceneModificationAccommodation = new Scene(vBoxModificationAccommodation, currentWidth, currentHeight);
            stage.setScene(sceneModificationAccommodation);
        }
        else if (button == navBarView.getUsersButton()){
            System.out.println("usersButton Button Clicked");
        }
        else if (button == navBarView.getDisconnectButton()){
            System.out.println("disconnectButton Button Clicked");
        }
        else if (button == modificationAccommodationView.getButtonSave()){
            System.out.println("button Save Clicked");
            modalStage.close();
        }
        else{
            // Handle other buttons if needed
        }
    }

    private void handleButtonClickTable(Button button, Integer id) {
        if (button == accommodationView.getDeleteButton()){
            System.out.println("getDeleteButton : " + id);
        }
        else if (button == accommodationView.getAddButton()){
            System.out.println("Add Button Clicked");

            VBox modalContent = modificationAccommodationView.createBox(new Accommodation(1, "Apparteent Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36));

            Scene modalScene = new Scene(modalContent, 1000, 500);
            modalStage.setScene(modalScene);

            modalStage.show();
        }
        else if (button == accommodationView.getModifyButton()){
            System.out.println("getModifyButton Button Clicked : " + id);

            VBox modalContent = modificationAccommodationView.createBox(new Accommodation(1, "Apparteent Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36));

            Scene modalScene = new Scene(modalContent, 1000, 500);
            modalStage.setScene(modalScene);

            modalStage.show();
        }
        else if (button == mailBoxView.getAcceptButton()){
            System.out.println("getAcceptButton Button Clicked : " + id);

        }
        else if (button == mailBoxView.getDeclineButton()){
            System.out.println("getDeclineButton Button Clicked : " + id);

        }
        else{
            System.out.println("other Button Clicked : " + id);
            // Handle other buttons if needed
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
