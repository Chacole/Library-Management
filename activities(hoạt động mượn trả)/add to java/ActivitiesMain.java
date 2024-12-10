package com.example.tuanq;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ActivitiesMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Tải file FXML
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/tuanq/activities.fxml"));
            // Cài đặt Scene và Stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Activities");
            primaryStage.setResizable(false); // Ngăn thay đổi kích thước
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
