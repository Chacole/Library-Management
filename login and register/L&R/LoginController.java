package com.example.tuanq;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue(); // Lấy vai trò được chọn

        // Kiểm tra thông tin đăng nhập
        if (role.equals("Admin")) {
            if (DatabaseConnection.checkPasswordOfAmin(email, password)) {
                showAlert(Alert.AlertType.INFORMATION, "Đăng nhập hành công!", "Chào mừng");
                // Chuyển đến màn hình Admin
                loadAdminDashboard();
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi!", "Email hoặc mật khẩu không đúng");
            }
        } else if (role.equals("User")) {
            if (DatabaseConnection.checkPasswordOfUser(email, password)) {
                showAlert(Alert.AlertType.INFORMATION, "Đăng nhập hành công!", "Chào mừng");
                // Chuyển đến màn hình User
                loadUserDashboard();
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi!", "Email hoặc mật khẩu không đúng");
            }
        }
    }

    private void loadAdminDashboard() {
        // Logic để chuyển đến màn hình Admin (nếu cần)
        System.out.println("Admin Dashboard Loaded");
    }

    private void loadUserDashboard() {
        // Logic để chuyển đến màn hình User (nếu cần)
        System.out.println("User Dashboard Loaded");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void switchToRegister(ActionEvent event) throws IOException {
        Stage stage = (Stage) emailField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/register.fxml"));
        stage.setScene(new Scene(loader.load()));
    }
}

