package com.eseo.lagence.lagence;


import javafx.application.Application;

import javafx.stage.Stage;

import java.io.IOException;



public class HelloApplication extends Application {

    private RequestService requestService;



    @Override
    public void start(Stage stage) throws IOException {
        RequestService requestService = RequestService.getInstance();
        RequestService.getInstance().login("/auth/login", "jules.dempt@outlook.fr", "Jules123");
        StageManager stageManager = StageManager.getInstance();
        stageManager.initStage(stage, StageManager.SceneView.LOGIN_SCENE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
