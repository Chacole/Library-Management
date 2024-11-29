package com.example.tuanq.handleMainButton;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Exit {
    public void exitSystem() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Existing system library");
        alert.setHeaderText("Existing... ");
        alert.setContentText("Choose your option ");

        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancle = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancle);
        Optional<ButtonType> rel = alert.showAndWait();
        if (rel.get() == buttonTypeYes) {
            System.exit(0);
        } else if (rel.get() == buttonTypeCancle) { }
    }
}
