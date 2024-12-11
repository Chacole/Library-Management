package com.example.tuanq;

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

    public static User getUserById(int UserID) throws Exception {
        try {
            Connection conn = ConnectDB.getConnection();

            String query = "SELECT * FROM users WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, UserID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getString("Phone"), ""
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//
    @FXML
    public void initialize() {
        int UserID = 1; // Sửa theo UserID đang login
        try {
            user = getUserById(UserID); // Lấy thông tin người dùng từ DB
            if (user != null) {
                nameLabel.setText(user.getName());
                emailLabel.setText(user.getEmail());
                Text addressText = new Text(user.getAddress());
                addressTextFlow.getChildren().add(addressText);
//                addressLabel.setText(user.getAddress());
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
