package com.eseo.lagence.lagence;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StageManager {

    private Stage modalStage;
    private Stage stage;
    private static StageManager instance;

    private NavBarView navBarView;

    private AccommodationView accommodationView;

    private MailBoxView mailBoxView;

    private ModificationAccommodationView modificationAccommodationView;

    private TenantView tenantView;

    private UsersView usersView;

    private Alert alert;

    public StageManager() {
        this.modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("");

        this.navBarView = new NavBarView();
        this.accommodationView = new AccommodationView();
        this.mailBoxView = new MailBoxView();
        this.modificationAccommodationView = new ModificationAccommodationView();
        this.tenantView = new TenantView();
        this.usersView = new UsersView();
        this.alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButton);
    }

    public static StageManager getInstance() {
        if(StageManager.instance == null) {
            StageManager.instance = new StageManager();
        }
        return StageManager.instance;
    }

    public void initStage(Stage stage, SceneView sceneView){
        this.stage = stage;

        setView(sceneView);

        Image icon = new Image(getClass().getResourceAsStream("/images/logo_Agence.png"));
        stage.getIcons().add(icon);
        stage.show();
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setTitle("L'Agence");
    }

    public Stage getStage(){
        return this.stage;
    }

    public enum SceneView{
        ACCOMMODATION_SCENE,
        MODIFY_ACCOMMODATION_SCENE,
        TENANT_SCENE,
        MAILBOX_SCENE,
        USERS_SCENE,
    }

    public void setView(SceneView sceneView) {
        System.out.println(sceneView);

        Scene lastScene = stage.getScene();

        VBox vBox = null;
        switch (sceneView){
            case ACCOMMODATION_SCENE -> {
                vBox = new VBox(navBarView.createNavBar(), accommodationView.createBox());
            }
            case MODIFY_ACCOMMODATION_SCENE -> {
                vBox = new VBox(navBarView.createNavBar(), modificationAccommodationView.createBox());
            }
            case TENANT_SCENE -> {
                vBox = new VBox(navBarView.createNavBar(), tenantView.createBox());
            }
            case MAILBOX_SCENE -> {
                vBox = new VBox(navBarView.createNavBar(), mailBoxView.createBox());
            }
            case USERS_SCENE -> {
                vBox = new VBox(navBarView.createNavBar(), usersView.createBox());
            }
        }

        if (lastScene != null) {
            double currentWidth = stage.getScene().getWidth();
            double currentHeight = stage.getScene().getHeight();
            setScene(new Scene(vBox, currentWidth, currentHeight));
        } else {
            setScene(new Scene(vBox));
        }
    }

    public void setScene(Scene scene) {
        this.stage.setScene(scene);
    }

    public void setModalScene(Scene scene) {
        this.modalStage.setScene(scene);
    }

    public boolean showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType okButton = new ButtonType("OK");

        alert.getButtonTypes().setAll(okButton);

        // Afficher la boîte de dialogue et attendre la réponse
        alert.showAndWait();

        return alert.getResult() == okButton;
    }
}
