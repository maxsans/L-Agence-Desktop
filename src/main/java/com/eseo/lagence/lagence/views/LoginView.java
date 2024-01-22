package com.eseo.lagence.lagence.views;

import com.eseo.lagence.lagence.utils.StageManager;
import com.eseo.lagence.lagence.services.RequestService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LoginView {
    private TextField email;
    private PasswordField password;

    public HBox create(){
        Text title = new Text("Bienvenue à l'Agence !");
        title.setStyle("-fx-font: 40 arial;");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label emailLabel = new Label("Adresse email :");
        grid.add(emailLabel, 0, 1);

        email = new TextField();
        email.setPrefWidth(250);
        email.setPrefHeight(30);
        grid.add(email, 1, 1);

        Label passwordLabel = new Label("Mot de passe :");
        grid.add(passwordLabel, 0, 2);

        password = new PasswordField();
        password.setPrefWidth(250);
        password.setPrefHeight(30);
        grid.add(password, 1, 2);

        Text errorMsg = new Text();
        errorMsg.setFill(Color.RED);
        errorMsg.setTranslateY(-10);
        errorMsg.setVisible(false);

        Button submit = new Button("Se connecter");
        submit.setOnAction(event -> {
            if (email.getText().trim().isEmpty()) {
                errorMsg.setText("Veuillez saisir une adresse e-mail.");
                errorMsg.setVisible(true);
                return;
            }

            if (password.getText().trim().isEmpty()) {
                errorMsg.setText("Veuillez saisir un mot de passe.");
                errorMsg.setVisible(true);
                return;
            }

            String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
            if (!email.getText().matches(emailRegex)) {
                errorMsg.setText("Veuillez saisir une adresse e-mail valide.");
                errorMsg.setVisible(true);
                return;
            }

            boolean credentialsValid = RequestService.getInstance().login( email.getText(), password.getText());
            if(credentialsValid){
                StageManager.getInstance().setView(StageManager.SceneView.ACCOMMODATION_SCENE);

            } else {
                errorMsg.setText("L'identifiant ou le mot de passe est incorrect.");
                errorMsg.setVisible(true);
            }
        });

        VBox vbox = new VBox(title, grid,errorMsg, submit);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(30));

        HBox hbox = new HBox(vbox);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }




}
