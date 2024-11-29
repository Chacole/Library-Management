package com.example.tuanq;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.beans.binding.Bindings;

import java.time.LocalDate;
import java.util.Date;

public class InformationBorrower {
    public void showDetailBorrowing() {
        // Tạo Dialog
        Dialog<BorrowRecord> dialog = new Dialog<>();
        dialog.setTitle("User Information Form");
        dialog.setHeaderText("Please enter your details below:");

        // Nút Submit và Cancel
        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        Button submitButton = (Button) dialog.getDialogPane().lookupButton(submitButtonType);
        submitButton.setDisable(true);

        TextField nameField = new TextField();
        nameField.setPromptText("userName");

        TextField titleField = new TextField();
        titleField.setPromptText("documentTitle");

        DatePicker borrowPicker = new DatePicker();
        borrowPicker.setPromptText("Date of Borrowing");

        DatePicker returnPicker = new DatePicker();
        returnPicker.setPromptText("Date of Returning");

        submitButton.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                nameField.getText().trim().isEmpty() ||
                                        titleField.getText().trim().isEmpty() ||
                                        borrowPicker.getValue() == null ||
                                        returnPicker.getValue() == null,
                        nameField.textProperty(),
                        titleField.textProperty(),
                        borrowPicker.valueProperty(),
                        returnPicker.valueProperty()
                )
        );

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Title:"), 0, 1);
        grid.add(titleField, 1, 1);
        grid.add(new Label("Date of Borrowing:"), 0, 2);
        grid.add(borrowPicker, 1, 2);
        grid.add(new Label("Date of Returning:"), 0, 3);
        grid.add(returnPicker, 1, 3);

        GridPane.setHgrow(nameField, Priority.ALWAYS);
        GridPane.setHgrow(titleField, Priority.ALWAYS);
        GridPane.setHgrow(borrowPicker, Priority.ALWAYS);
        GridPane.setHgrow(returnPicker, Priority.ALWAYS);

        dialog.getDialogPane().setContent(grid);

        // Xử lý khi nhấn nút Submit
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                String userName = nameField.getText();
                String documentTitle = titleField.getText();
                LocalDate borrowDate = borrowPicker.getValue();
                LocalDate returnDate = returnPicker.getValue();

                // Chuyển đổi LocalDate thành java.util.Date
                Date borrowDateConverted = java.util.Date.from(borrowDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
                Date returnDateConverted = java.util.Date.from(returnDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());

                // Trả về đối tượng BorrowRecord
                return new BorrowRecord(userName, documentTitle, borrowDateConverted, returnDateConverted);
            }
            return null; // Trả về null nếu nhấn Cancel
        });

        // Hiển thị dialog và nhận kết quả
        dialog.showAndWait().ifPresent(result -> {
            System.out.println("User Name: " + result.getUserName());
            System.out.println("Document Title: " + result.getDocumentTitle());
            System.out.println("Borrow Date: " + result.getFormattedBorrowDate());
            System.out.println("Return Date: " + result.getFormattedReturnDate());
        });
    }
}
