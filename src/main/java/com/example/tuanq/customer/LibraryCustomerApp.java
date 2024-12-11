package com.example.tuanq.customer;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LibraryCustomerApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Tải file FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("customerView.fxml"));
        Parent root = loader.load();

        // Tạo Scene và đặt vào Stage
        Scene scene = new Scene(root, 1200, 1000);

        // Thêm đường dẫn đến tệp CSS
        scene.getStylesheets().add(getClass().getResource("/com/example/tuanq/Styles.css").toExternalForm());

        primaryStage.setTitle("Library");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
