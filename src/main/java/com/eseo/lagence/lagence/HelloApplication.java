package com.eseo.lagence.lagence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class HelloApplication extends Application {

    private RequestService requestService;



    @Override
    public void start(Stage stage) throws IOException {
        RequestService requestService = RequestService.getInstance();
        RequestService.getInstance().login("/auth/login", "m@m.com", "azerty");
        StageManager stageManager = StageManager.getInstance();
        stageManager.initStage(stage, StageManager.SceneView.ACCOMMODATION_SCENE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
