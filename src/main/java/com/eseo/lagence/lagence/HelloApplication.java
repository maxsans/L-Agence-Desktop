package com.eseo.lagence.lagence;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Scene accommodationScene = new AccommodationScene().createScene();

        stage.setTitle("L'Agence");
        stage.setScene(accommodationScene);
        stage.setMinWidth(1450.00);
        stage.setMinHeight(300.00);

        Image icon = new Image(getClass().getResourceAsStream("/images/logo_Agence.png"));
        stage.getIcons().add(icon);

        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
