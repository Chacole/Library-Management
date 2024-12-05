package com.example.tuanq;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/tuanq/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login and Register Example");
        stage.setScene(scene);
        stage.setResizable(false); // Ngăn thay đổi kích thước
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
