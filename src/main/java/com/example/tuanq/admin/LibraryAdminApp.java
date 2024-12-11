package com.example.tuanq.admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LibraryAdminApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Tải file FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminView.fxml"));
        Parent root = loader.load();

        // Tạo Scene
        Scene scene = new Scene(root, 1000, 1200);

        // Thêm đường dẫn đến tệp CSS
        scene.getStylesheets().add(getClass().getResource("/com/example/tuanq/Styles.css").toExternalForm());

        primaryStage.setTitle("Library Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

