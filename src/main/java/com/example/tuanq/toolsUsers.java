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

public class toolsUsers{
    @FXML
    private Button buttonPlus;

    @FXML
    private Button buttonMinus;

    @FXML
    private Button buttonUpdate;

    @FXML
    private void handleButtonPlus() throws IOException {
        // Tạo Dialog
        Dialog<Users> dialog = new Dialog<>();
        dialog.setTitle("Add User Form");
        dialog.setHeaderText("Please enter your details below:");

        // Thêm các nút OK và Cancel
        ButtonType okButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        Button submitButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
        submitButton.setDisable(true);

        dialog.getDialogPane().setPrefSize(400, 500);

        // Tạo các trường nhập liệu
        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField addressField = new TextField();
        addressField.setPromptText("Adress");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");
        phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                phoneField.setText(oldValue); // Chỉ cho phép số
            }
        });

        submitButton.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                nameField.getText().trim().isEmpty() ||
                                        emailField.getText().trim().isEmpty() ||
                                        addressField.getText().trim().isEmpty() ||
                                        phoneField.getText().trim().isEmpty(),
                        nameField.textProperty(),
                        emailField.textProperty(),
                        addressField.textProperty(),
                        phoneField.textProperty()
                )
        );

        // Sắp xếp giao diện các trường nhập trong GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(20);

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);

        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);

        grid.add(new Label("Adress:"), 0, 2);
        grid.add(addressField, 1, 2);

        grid.add(new Label("Phone:"), 0, 3);
        grid.add(phoneField, 1, 3);

        GridPane.setHgrow(nameField, Priority.ALWAYS);
        GridPane.setHgrow(emailField, Priority.ALWAYS);
        GridPane.setHgrow(addressField, Priority.ALWAYS);
        GridPane.setHgrow(phoneField, Priority.ALWAYS);

        dialog.getDialogPane().setContent(grid);

        // Xử lý kết quả
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                String name = nameField.getText();
                String email = emailField.getText();
                String address = addressField.getText();
                String phone = phoneField.getText();

                return new Users(name, email, address, phone);
            }
            return null;
        });

        // Hiển thị Dialog và nhận kết quả
        Optional<Users> result = dialog.showAndWait();
        result.ifPresent(userData -> {
            System.out.println("Name: " + userData.getName());
            System.out.println("Email: " + userData.getEmail());
            System.out.println("Address: " + userData.getAddress());
            System.out.println("Phone: " + userData.getPhone());

            UserUtil userutil = new UserUtil();
            int rowsAdded = userutil.insert(userData);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add user");
            if (rowsAdded > 0) {
                alert.setHeaderText("Success");
                alert.setContentText("user added successfully.");

            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setHeaderText("Failed");
                alert.setContentText("Failed to add user. Please try again.");
            }
            alert.showAndWait();
        });

    }

    @FXML
    private void handleButtonMinus() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete User Form");
        dialog.setHeaderText("Enter the user details to delete");
        dialog.setContentText("Enter details in format: Name,Email");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                String[] details = result.get().split(",");
                if (details.length != 2) {
                    throw new IllegalArgumentException("Invalid input format. Use: Name,Email");
                }

                String name = details[0].trim();
                String email = details[1].trim();

                Users userToDelete = new Users(name, email, "", "");

                UserUtil userutil = new UserUtil();
                userutil.delete(userToDelete);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleButtonUpdate() {
        // Tạo Dialog
        Dialog<Users> dialog = new Dialog<>();
        dialog.setTitle("User Information Form");
        dialog.setHeaderText("Please enter user details to update:");

        // Thêm các nút OK và Cancel
        ButtonType okButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        Button submitButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
        submitButton.setDisable(true);

        dialog.getDialogPane().setPrefSize(400, 300);

        // Tạo các trường nhập liệu
        TextField IDField = new TextField();
        IDField.setPromptText("ID");
        IDField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                IDField.setText(oldValue); // Chỉ cho phép số
            }
        });

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");
        phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                phoneField.setText(oldValue); // Chỉ cho phép số
            }
        });

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

        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);

        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);

        grid.add(new Label("Address:"), 0, 3);
        grid.add(addressField, 1, 3);

        grid.add(new Label("Phone:"), 0, 4);
        grid.add(phoneField, 1, 4);

        GridPane.setHgrow(IDField, Priority.ALWAYS);
        GridPane.setHgrow(nameField, Priority.ALWAYS);
        GridPane.setHgrow(emailField, Priority.ALWAYS);
        GridPane.setHgrow(addressField, Priority.ALWAYS);
        GridPane.setHgrow(phoneField, Priority.ALWAYS);

        dialog.getDialogPane().setContent(grid);

        // Xử lý kết quả
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                try {
                    int ID = Integer.parseInt(IDField.getText().trim());
                    String name = nameField.getText().trim();
                    String email = emailField.getText().trim();
                    String address = addressField.getText().trim();
                    String phone = phoneField.getText().trim();

                    Users user = new Users(name, email, address, phone);
                    user.setID(ID);

                    return user;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return null; // Nếu có lỗi, trả về null
                }
            }
            return null;
        });

        // Hiển thị Dialog và nhận kết quả
        Optional<Users> result = dialog.showAndWait();
        result.ifPresent(userData -> {
            System.out.println("ID: " + userData.getID());
            System.out.println("Name: " + userData.getName());
            System.out.println("Email: " + userData.getEmail());
            System.out.println("Address: " + userData.getAddress());
            System.out.println("Phone: " + userData.getPhone());

            UserUtil userUtil = new UserUtil();
            int rowsUpdated = userUtil.update(userData);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update User");
            if (rowsUpdated > 0) {
                alert.setHeaderText("Success");
                alert.setContentText("User updated successfully.");
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setHeaderText("Failed");
                alert.setContentText("Failed to update user. Please try again.");
            }
            alert.showAndWait();
        });
    }
}
