package com.example.tuanq;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DocumentFormController {
    @FXML
    public TextField authorField, titleField, typeField, yearField, quantityField, imagePathField;

    public Documents document;

    public Documents getDocument() {
        return document;
    }

    @FXML
    private void handleSave() {
        try {
            // Lấy dữ liệu từ form
            String author = authorField.getText();
            String title = titleField.getText();
            String type = typeField.getText();
            int year = Integer.parseInt(yearField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            String imagePath = imagePathField.getText();

            // Tạo Document mới
            document = new Documents(author, title, type, year, quantity, imagePath);

            // Đóng cửa sổ
            ((Stage) authorField.getScene().getWindow()).close();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }
}
