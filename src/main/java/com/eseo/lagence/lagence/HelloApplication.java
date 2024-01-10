package com.eseo.lagence.lagence;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {

    private NavBarView navBarView;

    private AccommodationView accommodationView;

    private ModificationAccommodationView modificationAccommodationView;

    private TenantView tenantView;

    private Stage stage;

    private Stage modalStage;



    @Override
    public void start(Stage stage) throws IOException {

        this.navBarView = new NavBarView(this::handleButtonClick);
        this.accommodationView = new AccommodationView(this::handleButtonClickTableAccommodation);
        this.modificationAccommodationView = new ModificationAccommodationView(this::handleButtonClick);
        this.tenantView = new TenantView();

        this.modalStage = new Stage();

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


    private void handleButtonClick(Button button) {
        double currentWidth = stage.getScene().getWidth();
        double currentHeight = stage.getScene().getHeight();
        if (button == navBarView.getListAccommodationsButton()){
            VBox vBoxAccommodation = new VBox(navBarView.getNavBar(), accommodationView.createBox());
            Scene sceneAccommodation = new Scene(vBoxAccommodation, currentWidth, currentHeight);
            stage.setScene(sceneAccommodation);
        }
        else if (button == navBarView.getBedButton()){
            VBox vBoxTenant = new VBox(navBarView.getNavBar(), tenantView.createBox());
            Scene sceneTenant = new Scene(vBoxTenant, currentWidth, currentHeight);
            stage.setScene(sceneTenant);
        }
        else if (button == navBarView.getMailboxButton()){
            VBox vBoxModificationAccommodation = new VBox(navBarView.getNavBar(), modificationAccommodationView.createBox(new Accommodation(1, "Apparteent Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36, true, new UserAccount(1, "test@gmail.com", "test", "T"))));
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

    private void handleButtonClickTableAccommodation(Button button, Integer id) {
        if (button == accommodationView.getDeleteButton()){
            System.out.println("getDeleteButton : " + id);
        }
        else if (button == accommodationView.getAddButton()){
            System.out.println("Add Button Clicked");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Modal Window");

            VBox modalContent = modificationAccommodationView.createBox(new Accommodation(1, "Apparteent Doutre", 12.22, "bla bla bla", "2 rue TB", 2 , 36, true, new UserAccount(1, "test@gmail.com", "test", "T")));

            Scene modalScene = new Scene(modalContent, 1000, 500);
            modalStage.setScene(modalScene);

            modalStage.show();
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
