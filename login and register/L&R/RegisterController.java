package com.example.tuanq;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private void handleRegister(ActionEvent event) throws IOException {
        String username = nameField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!password.equals(confirmPassword)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Mật khẩu không khớp");
            alert.showAndWait();
            return;
        }

        if (UserUtil.getInstance().insert(new Users(username, email, address, phone)) > 0 &&
                UserUtil.getInstance().addPasswordToAccount(email, password) > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Đăng ký thành công!");
            alert.showAndWait();
        }

        // Quay lại màn hình Login
        switchToLogin(event);
    }

    @FXML
    private void switchToLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) emailField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/login.fxml"));
        stage.setScene(new Scene(loader.load()));
    }
}
