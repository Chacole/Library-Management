package com.example.tuanq;

import com.example.tuanq.admin.LibraryAdminApp;
import com.example.tuanq.customer.FindNameAfterSignUp;
import com.example.tuanq.customer.LibraryCustomerApp;
import com.example.tuanq.customer.Profile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
            if (CheckPassword.checkPasswordOfAmin(email, password)) {
                Profile profile = Profile.getInstance();
                profile.setEmail(email);
                profile.setRole("Admin");

                loadAdminDashboard();
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi!", "Email hoặc mật khẩu không đúng");
            }
        } else if (role.equals("User")) {
            if (CheckPassword.checkPasswordOfUser(email, password)) {
                String username = FindNameAfterSignUp.getUsernameByEmail(email);
                if (username == null || username.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi!", "Không tìm thấy username tương ứng với email.");
                    return;
                } else {
                    System.out.println(username);
                }

                Profile profile = Profile.getInstance();
                profile.setEmail(email);
                profile.setUsername(username);
                profile.setRole("User");

                // Chuyển đến màn hình User
                loadUserDashboard();
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi!", "Email hoặc mật khẩu không đúng");
            }
        }
    }

    private void loadAdminDashboard() {
        try {
            LibraryAdminApp libraryApp = new LibraryAdminApp();
            Stage stage = (Stage) emailField.getScene().getWindow();
            libraryApp.start(stage);

            // Đặt kích thước Stage tùy chỉnh
            stage.setWidth(1200); // Chiều rộng
            stage.setHeight(800); // Chiều cao

            // Đặt Stage ở chính giữa màn hình
            stage.centerOnScreen();

            // Cho phép thay đổi kích thước nếu cần
            stage.setResizable(true);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi!", "Không thể tải giao diện Admin Dashboard.");
        }
    }

    private void loadUserDashboard() {
        try {
            LibraryCustomerApp libraryApp = new LibraryCustomerApp();
            Stage stage = (Stage) emailField.getScene().getWindow();
            libraryApp.start(stage);

            // Đặt kích thước Stage tùy chỉnh
            stage.setWidth(1200); // Chiều rộng
            stage.setHeight(800); // Chiều cao

            // Đặt Stage ở chính giữa màn hình
            stage.centerOnScreen();

            // Cho phép thay đổi kích thước nếu cần
            stage.setResizable(true);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi!", "Không thể tải giao diện Admin Dashboard.");
        }
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
