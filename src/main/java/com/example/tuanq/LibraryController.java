package com.example.tuanq;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LibraryController {

    @FXML
    private Button exitButton;


    @FXML
    private VBox contentBox;

    // Các phương thức xử lý sự kiện
    @FXML
    private void handleExit() {
        System.exit(0); // Thoát chương trình
    }

    @FXML
    private void handleAddDocument() {
        // Xóa nội dung cũ và thêm nội dung mới
        contentBox.getChildren().clear();
        contentBox.getChildren().add(new Label("Add Document Functionality"));
    }

    @FXML
    private void handleRemoveDocument() {
        contentBox.getChildren().clear();
        contentBox.getChildren().add(new Label("Remove Document Functionality"));
    }

    @FXML
    private void handleUpdateDocument() {
        contentBox.getChildren().clear();
        contentBox.getChildren().add(new Label("Update Document Functionality"));
    }

    @FXML
    private void handleFindDocument() {
        contentBox.getChildren().clear();
        contentBox.getChildren().add(new Label("Find Document Functionality"));
    }

    @FXML
    private void handleDisplayDocument() {
        contentBox.getChildren().clear();
        contentBox.getChildren().add(new Label("Display Document Functionality"));
    }

    @FXML
    private void handleDisplayUser() {
        contentBox.getChildren().clear();

        Displayusers displayUsers = new Displayusers();
        contentBox.getChildren().add(displayUsers.createUserTable());
    }

    @FXML
    private void handleBorrowDocument() {
        contentBox.getChildren().clear();
        contentBox.getChildren().add(new Label("Borrow Document Functionality"));
    }

    @FXML
    private void handleReturnDocument() {
        contentBox.getChildren().clear();
        contentBox.getChildren().add(new Label("Return Document Functionality"));
    }

    @FXML
    private void handleBack() {
        System.out.println("Back button clicked!");
    }

    @FXML
    private void handleForward() {
        System.out.println("Forward button clicked!");
    }

}