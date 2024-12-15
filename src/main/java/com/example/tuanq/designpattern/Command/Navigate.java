package com.example.tuanq.designpattern.Command;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navigate {
    private Stage stage;

    public Navigate(Stage stage) {
        this.stage = stage;
    }

    public void switchToScene(Scene scene) {
        stage.setScene(scene);
        stage.show();
    }
}