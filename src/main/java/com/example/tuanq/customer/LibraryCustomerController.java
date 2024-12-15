package com.example.tuanq.customer;

import com.example.tuanq.Main;
import com.example.tuanq.admin.DisplayBorrowRecord;
import com.example.tuanq.admin.Exit;
import com.example.tuanq.designpattern.Command.Command;
import com.example.tuanq.designpattern.Command.ConcreteCommand;
import com.example.tuanq.designpattern.Command.NavigationSystem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LibraryCustomerController {
    @FXML
    private VBox contentBox;

    @FXML
    private Button buttonSignOut;

    @FXML
    private Button buttonProfile;

    @FXML
    private Button handleExit;

    @FXML
    private Button handleDisplayDocument;

    @FXML
    private Button handleBorrowDocument;

    protected NavigationSystem navigationSystem = new NavigationSystem(Main.getPrimaryStage());

    @FXML
    private void handleProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/profile.fxml"));
            Parent root = loader.load();

            Stage profileStage = new Stage();
            profileStage.setTitle("User Profile");

            // Đặt Scene cho Stage
            Scene profileScene = new Scene(root, 400, 300);
            profileStage.setScene(profileScene);

            // Đặt cửa sổ mới ở giữa màn hình
            profileStage.centerOnScreen();

            // Hiển thị cửa sổ
            profileStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSignOut() {
        Profile.getInstance().clearSession();

        try {
            Stage stage = Main.getPrimaryStage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/login.fxml"));
            Scene loginScene = new Scene(loader.load());

            stage.setWidth(800);
            stage.setHeight(600);
            stage.centerOnScreen();

            // Tạo Command để chuyển đổi cảnh
            Command switchToLoginScene = new ConcreteCommand(new NavigationSystem(stage), loginScene);

            // Thực thi Command
            switchToLoginScene.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExit() {
        Exit exit = new Exit();
        exit.exitSystem();
    }

    @FXML
    private void handleDisplayDocument() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/customer/documents.fxml"));
            Parent documentView = loader.load();

            Displaydocuments controller = loader.getController();
            controller.initialize(); // Đảm bảo gọi phương thức initialize để hiển thị đúng

            contentBox.getChildren().clear();
            contentBox.getChildren().add(documentView);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBorrowDocument() {
        contentBox.getChildren().clear();
        DisplayBorrowRecord displayborrowrecord = new DisplayBorrowRecord();
        contentBox.getChildren().add(displayborrowrecord.createBorrowRecordTable());
    }
}