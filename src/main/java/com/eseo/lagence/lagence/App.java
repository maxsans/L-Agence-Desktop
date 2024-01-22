package com.eseo.lagence.lagence;


import com.eseo.lagence.lagence.services.RequestService;
import com.eseo.lagence.lagence.utils.StageManager;
import javafx.application.Application;

import javafx.stage.Stage;

import java.io.IOException;



public class App extends Application {

    private RequestService requestService;



    @Override
    public void start(Stage stage) throws IOException {
        RequestService requestService = RequestService.getInstance();
        RequestService.getInstance();
        StageManager stageManager = StageManager.getInstance();
        stageManager.initStage(stage, StageManager.SceneView.LOGIN_SCENE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
