package com.eseo.lagence.lagence;


import javafx.application.Application;

import javafx.stage.Stage;

import java.io.IOException;



public class HelloApplication extends Application {

    private RequestService requestService;



    @Override
    public void start(Stage stage) throws IOException {
<<<<<<< Updated upstream
        RequestService requestService = RequestService.getInstance();
        RequestService.getInstance().login("/auth/login", "m@m.com", "azerty");
=======
        RequestService.getInstance();
>>>>>>> Stashed changes
        StageManager stageManager = StageManager.getInstance();
        stageManager.initStage(stage, StageManager.SceneView.ACCOMMODATION_SCENE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
