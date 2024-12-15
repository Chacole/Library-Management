package com.example.tuanq.customer;

import com.example.tuanq.DatabaseConnection;
import com.example.tuanq.admin.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserProfileController {

    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private TextFlow addressTextFlow;
    @FXML
    private Label phoneLabel;

    private Users user;

    public static Users getUserByName(String userName) throws Exception {
        try {
            Connection conn = DatabaseConnection.getConnection();

            String query = "SELECT * FROM users WHERE Name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userName);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Users(
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getString("Phone")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    public void initialize() {
        Profile profile = Profile.getInstance();
        String userName = profile.getUsername();

        try {
            user = getUserByName(userName); // Lấy thông tin người dùng từ DB
            if (user != null) {
                nameLabel.setText(userName);
                emailLabel.setText(user.getEmail());
                Text addressText = new Text(user.getAddress());
                addressTextFlow.getChildren().add(addressText);
                phoneLabel.setText(user.getPhone());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEdit(ActionEvent event) throws IOException {
        switchToEdit(event);
    }

    @FXML
    private void switchToEdit(ActionEvent event) throws IOException {
        Stage stage = (Stage) emailLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/profile_edit.fxml"));
        stage.setScene(new Scene(loader.load()));
    }
}