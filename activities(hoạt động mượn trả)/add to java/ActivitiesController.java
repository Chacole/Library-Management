package com.example.tuanq;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ActivitiesController {

    @FXML
    private TableView<Activity> tableView;
    @FXML
    private TableColumn<Activity, String> usernameColumn;
    @FXML
    private TableColumn<Activity, String> requestColumn;

    @FXML
    private TableColumn<Activity, String> documentColumn;

    @FXML
    private TableColumn<Activity, String> statusColumn;

    @FXML
    private TableColumn<Activity, String> timeColumn;

    @FXML
    private void handleDelete() {
        int UserID = 1; // Sửa theo UserID đang login

        int t = deleteActivities(UserID);

        // Load data from database
        tableView.setItems(loadActivites());

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Xóa thành công!");
        alert.showAndWait();
    }

    private int deleteActivities(int UserID) {
        int deletedRows = 0;
        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "CALL DeleteActivities (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, UserID);

            deletedRows = preparedStatement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deletedRows;
    }

    public void initialize() {
        // Thiết lập các cột cho TableView
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        requestColumn.setCellValueFactory(new PropertyValueFactory<>("Request"));
        documentColumn.setCellValueFactory(new PropertyValueFactory<>("Document"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("Time"));

        // Load data from database
        tableView.setItems(loadActivites());
    }

    private ObservableList<Activity> loadActivites() {
        ObservableList<Activity> activities = FXCollections.observableArrayList();
        int UserID = 1; // Sửa theo UserID đang login

        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "SELECT * FROM Activities WHERE UserID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, UserID);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Activity nActivity = new Activity(rs.getString("UserName"),
                        rs.getString("Request"), rs.getString("DocumentTitle"),
                        rs.getString("Status"), rs.getString("Time"));
                activities.add(nActivity);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activities;
    }
}
