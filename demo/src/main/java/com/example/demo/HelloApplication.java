package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Button.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 200, 150);
        URL cssFile = getClass().getResource("style.css");
        if (cssFile != null) {
            scene.getStylesheets().add(cssFile.toExternalForm());
        }
        stage.setTitle("TestEffect");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}