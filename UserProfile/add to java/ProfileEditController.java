package com.example.tuanq;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileEditController {
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
    private void handleConfirm(ActionEvent event) throws IOException {
        String username = nameField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();

        int UserID = 1; // Sửa theo UserID đang login

        if (!email.isEmpty() && !EmailValidator.isValidGmail(email)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sai form email([a-z,A-Z,0-9,._%+-]@gmail.com)");
            alert.showAndWait();
        } else if (CheckEmail.isEmailExist(email)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Email đã tồn tại");
            alert.showAndWait();
        } else if (!phone.isEmpty() && !CheckPhone.isValidPhoneNumber(phone)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sai form phone([0-9])");
            alert.showAndWait();
        } else if (!CheckPassword.checkPasswordForEdit(UserID , password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sai mật khẩu");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Chỉnh sửa thành công!");
            alert.showAndWait();
            int t = UserUtil.getInstance().update(new Users(UserID, username, email, address, phone));
            // Quay lại màn hình profile
            switchToProfile(event);
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        switchToProfile(event);
    }

    @FXML
    private void switchToProfile(ActionEvent event) throws IOException {
        Stage stage = (Stage) emailField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/profile.fxml"));
        stage.setScene(new Scene(loader.load()));
    }
}
