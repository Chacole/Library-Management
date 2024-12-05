package com.example.tuanq;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

import java.io.IOException;

public class toolsDocuments {
    @FXML
    private Button buttonPlus;

    @FXML
    private Button buttonMinus;

    @FXML
    private Button buttonUpdate;

    @FXML
    private void handleButtonPlus() throws IOException {
        // Tạo Dialog
        Dialog<Documents> dialog = new Dialog<>();
        dialog.setTitle("Add Document Form");
        dialog.setHeaderText("Please enter your details below:");

        // Thêm các nút OK và Cancel
        ButtonType okButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        Button submitButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
        submitButton.setDisable(true);

        dialog.getDialogPane().setPrefSize(400, 500);

        // Tạo các trường nhập liệu
        TextField authorField = new TextField();
        authorField.setPromptText("Author");

        TextField titleField = new TextField();
        titleField.setPromptText("Title");

        TextField typeField = new TextField();
        typeField.setPromptText("Type");

        TextField yearPublishedField = new TextField();
        yearPublishedField.setPromptText("YearPublished");
        yearPublishedField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                yearPublishedField.setText(oldValue); // Chỉ cho phép số
            }
        });

        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");
        quantityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantityField.setText(oldValue); // Chỉ cho phép số
            }
        });

        TextField imagePathField = new TextField();
        imagePathField.setPromptText("imagePath");

        submitButton.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                authorField.getText().trim().isEmpty() ||
                                        titleField.getText().trim().isEmpty() ||
                                        typeField.getText().trim().isEmpty() ||
                                        yearPublishedField.getText().trim().isEmpty() ||
                                        quantityField.getText().trim().isEmpty(),
                        authorField.textProperty(),
                        titleField.textProperty(),
                        typeField.textProperty(),
                        yearPublishedField.textProperty(),
                        quantityField.textProperty()
                )
        );

        // Sắp xếp giao diện các trường nhập trong GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(20);

        grid.add(new Label("Author:"), 0, 0);
        grid.add(authorField, 1, 0);

        grid.add(new Label("Title:"), 0, 1);
        grid.add(titleField, 1, 1);

        grid.add(new Label("Type:"), 0, 2);
        grid.add(typeField, 1, 2);

        grid.add(new Label("Year:"), 0, 3);
        grid.add(yearPublishedField, 1, 3);

        grid.add(new Label("Quantity:"), 0, 4);
        grid.add(quantityField, 1, 4);

        grid.add(new Label("Image Path:"), 0, 5);
        grid.add(imagePathField, 1, 5);

        GridPane.setHgrow(authorField, Priority.ALWAYS);
        GridPane.setHgrow(titleField, Priority.ALWAYS);
        GridPane.setHgrow(typeField, Priority.ALWAYS);
        GridPane.setHgrow(yearPublishedField, Priority.ALWAYS);
        GridPane.setHgrow(quantityField, Priority.ALWAYS);
        GridPane.setHgrow(imagePathField, Priority.ALWAYS);

        dialog.getDialogPane().setContent(grid);

        // Xử lý kết quả
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                String author = authorField.getText();
                String title = titleField.getText();
                String type = typeField.getText();
                int year = Integer.parseInt(yearPublishedField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                String image = imagePathField.getText();
                return new Documents(author, title, type, year, quantity, image);
            }
            return null;
        });

        // Hiển thị Dialog và nhận kết quả
        Optional<Documents> result = dialog.showAndWait();
        result.ifPresent(userData -> {
            System.out.println("Author: " + userData.getAuthor());
            System.out.println("Title: " + userData.getTitle());
            System.out.println("Type: " + userData.getType());
            System.out.println("YearPublished: " + userData.getYear());
            System.out.println("Quantity: " + userData.getQuantity());

            DocumentUtil documentutil = new DocumentUtil();
            int rowsAdded = documentutil.insert(userData);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Document");
            if (rowsAdded > 0) {
                alert.setHeaderText("Success");
                alert.setContentText("Document added successfully.");

            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setHeaderText("Failed");
                alert.setContentText("Failed to add document. Please try again.");
            }
            alert.showAndWait();
        });

    }

    @FXML
    private void handleButtonMinus() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Document Form");
        dialog.setHeaderText("Enter the document details to delete");
        dialog.setContentText("Enter details in format: Title,Author,Year");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                String[] details = result.get().split(",");
                if (details.length != 3) {
                    throw new IllegalArgumentException("Invalid input format. Use: Title,Author,Year");
                }

                String title = details[0].trim();
                String author = details[1].trim();
                int year = Integer.parseInt(details[2].trim());

                Documents documentToDelete = new Documents(author, title, "", year, 0, "");

                DocumentUtil documentutil = new DocumentUtil();
                int rowsDeleted = documentutil.delete(documentToDelete);
                if (rowsDeleted > 0) {
                    System.out.println("Document deleted successfully.");
                } else {
                    System.out.println("Failed to delete document. Document not found.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleButtonUpdate() {
        // Tạo Dialog
        Dialog<Documents> dialog = new Dialog<>();
        dialog.setTitle("Document Information Form");
        dialog.setHeaderText("Please enter your details below:");

        // Thêm các nút OK và Cancel
        ButtonType okButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        Button submitButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
        submitButton.setDisable(true);

        dialog.getDialogPane().setPrefSize(400, 500);

        // Tạo các trường nhập liệu
        TextField IDField = new TextField();
        IDField.setPromptText("ID");
        IDField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                IDField.setText(oldValue); // Chỉ cho phép số
            }
        });

        TextField authorField = new TextField();
        authorField.setPromptText("Author");

        TextField titleField = new TextField();
        titleField.setPromptText("Title");

        TextField typeField = new TextField();
        typeField.setPromptText("Type");

        TextField yearPublishedField = new TextField();
        yearPublishedField.setPromptText("YearPublished");
        yearPublishedField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                yearPublishedField.setText(oldValue); // Chỉ cho phép số
            }
        });

        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");
        quantityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantityField.setText(oldValue); // Chỉ cho phép số
            }
        });

        TextField imagePathField = new TextField();
        imagePathField.setPromptText("imagePath");

        submitButton.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                        IDField.getText().trim().isEmpty(),
                        IDField.textProperty()
                )
        );

        // Sắp xếp giao diện các trường nhập trong GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(20);

        grid.add(new Label("ID:"), 0, 0);
        grid.add(IDField, 1, 0);

        grid.add(new Label("Author:"), 0, 1);
        grid.add(authorField, 1, 1);

        grid.add(new Label("Title:"), 0, 2);
        grid.add(titleField, 1, 2);

        grid.add(new Label("Type:"), 0, 3);
        grid.add(typeField, 1, 3);

        grid.add(new Label("Year:"), 0, 4);
        grid.add(yearPublishedField, 1, 4);

        grid.add(new Label("Quantity:"), 0, 5);
        grid.add(quantityField, 1, 5);

        grid.add(new Label("Image Path:"), 0, 6);
        grid.add(imagePathField, 1, 6);

        GridPane.setHgrow(IDField, Priority.ALWAYS);
        GridPane.setHgrow(authorField, Priority.ALWAYS);
        GridPane.setHgrow(titleField, Priority.ALWAYS);
        GridPane.setHgrow(typeField, Priority.ALWAYS);
        GridPane.setHgrow(yearPublishedField, Priority.ALWAYS);
        GridPane.setHgrow(quantityField, Priority.ALWAYS);
        GridPane.setHgrow(imagePathField, Priority.ALWAYS);

        dialog.getDialogPane().setContent(grid);

        // Xử lý kết quả
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                try {
                    int ID = Integer.parseInt(IDField.getText().trim());
                    String author = authorField.getText().trim();
                    String title = titleField.getText().trim();
                    String type = typeField.getText().trim();

                    // Sử dụng -1 nếu field để trống
                    int year = yearPublishedField.getText().trim().isEmpty() ? -1 : Integer.parseInt(yearPublishedField.getText().trim());
                    int quantity = quantityField.getText().trim().isEmpty() ? -1 : Integer.parseInt(quantityField.getText().trim());

                    String imagePath = imagePathField.getText().trim();

                    Documents document = new Documents(author, title, type, year, quantity, imagePath);
                    document.setID(ID);
                    return document;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return null; // Nếu có lỗi, trả về null
                }
            }
            return null;
        });

        // Hiển thị Dialog và nhận kết quả
        Optional<Documents> result = dialog.showAndWait();
        result.ifPresent(userData -> {
            System.out.println("ID: " + userData.getID());
            System.out.println("Author: " + userData.getAuthor());
            System.out.println("Title: " + userData.getTitle());
            System.out.println("Type: " + userData.getType());
            System.out.println("YearPublished: " + userData.getYear());
            System.out.println("Quantity: " + userData.getQuantity());
            System.out.println("Image: " + userData.getUrl());

            DocumentUtil documentutil = new DocumentUtil();
            int rowsUpdated = documentutil.update(userData);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Document");
            if (rowsUpdated > 0) {
                alert.setHeaderText("Success");
                alert.setContentText("Document updated successfully.");
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setHeaderText("Failed");
                alert.setContentText("Failed to update document. Please try again.");
            }
            alert.showAndWait();
        });

    }
}
