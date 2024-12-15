package com.example.tuanq.designpattern.Command;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationSystem {
    private Stage stage;

    public NavigationSystem(Stage stage) {
        this.stage = stage;
    }

    public void switchToScene(Scene scene) {
        stage.setScene(scene);
        stage.show();
    }
}