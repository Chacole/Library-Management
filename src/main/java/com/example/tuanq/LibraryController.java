package com.example.tuanq;
import com.example.tuanq.handleMainButton.Exit;
import com.example.tuanq.handleMainButton.Displayusers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class LibraryController {
    @FXML
    private VBox contentBox;

    // Các phương thức xử lý sự kiện
    @FXML
    private void handleExit() {
        Exit exit = new Exit();
        exit.exitSystem();
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
        try {
            Parent documentView = FXMLLoader.load(getClass().getResource("/com/example/tuanq/documents.fxml"));
            contentBox.getChildren().clear();
            contentBox.getChildren().add(documentView);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        DisplayBorrowRecord displayborrowrecord = new DisplayBorrowRecord();
        contentBox.getChildren().add(displayborrowrecord.createBorrowRecordTable());
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