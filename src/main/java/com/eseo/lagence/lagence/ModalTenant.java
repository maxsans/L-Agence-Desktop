package com.eseo.lagence.lagence;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalTenant {
    private Stage modalStage;

    public void openModal(Accommodation selectedAccommodation) {
        modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setResizable(false);

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        Label infoLabel = new Label("Information: " + selectedAccommodation.getId());

        vbox.getChildren().add(infoLabel);
        Scene modalScene = new Scene(vbox, 1000,500); // Taille fixe

        modalStage.setScene(modalScene);
        modalStage.setTitle("Fiche locataire");

        modalStage.show();
    }

    public void close() {
        if (modalStage != null) {
            modalStage.close();
        }
    }
}

