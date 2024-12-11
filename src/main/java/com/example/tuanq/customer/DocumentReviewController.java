package com.example.tuanq.customer;

import com.example.tuanq.DatabaseConnection;
import com.example.tuanq.admin.Review;
import com.example.tuanq.admin.ReviewUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.Rating;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class DocumentReviewController implements Initializable {

    @FXML
    private Rating rating;
    @FXML
    private Label msg;

    @FXML
    private ImageView imageView;

    @FXML
    private TextArea commentArea;

    @FXML
    private TableView<Review> tableView;
    @FXML
    private TableColumn<Review, String> usernameColumn;
    @FXML
    private TableColumn<Review, String> commentColumn;

    private double ratingNumber;

    // Xử lý sự kiện nhấn nút gửi đánh giá
    @FXML
    private void handleSubmit() {
        String comment = commentArea.getText();
        int DocumentID = 1; // Sửa theo DocumentID mà nhấp vào
        int UserID = 1; // Sửa theo UserID nhớ từ login
        ReviewUtil.getInstance().insert(new Review(0, DocumentID, UserID, "", ratingNumber, comment));

        // Load data from database
        tableView.setItems(loadDocumentReviews());

        // Hiển thị hộp thoại thông báo đánh giá và bình luận
        showAlert("Đánh giá của bạn",  "Đánh giá: " + ratingNumber + "\nBình luận: " + comment);
    }

    // Hàm hiển thị hộp thoại thông báo
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Listener cho Rating
        rating.ratingProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                msg.setText("Rating : "+ t1.toString());
                ratingNumber = Double.parseDouble(t1.toString());
            }
        });

        // Thiết lập các cột cho TableView
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("Comment"));

        // Load data from database
        tableView.setItems(loadDocumentReviews());
    }

    private ObservableList<Review> loadDocumentReviews() {
        ObservableList<Review> reviews = FXCollections.observableArrayList();
        int DocumentID = 1; // Sửa theo DocumentID mà nhấp vào

        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "SELECT UserID, UserName, Rating, Comment FROM DocumentReviews WHERE DocumentID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, DocumentID);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Review nReview = new Review(0, DocumentID,
                        rs.getInt("UserID"), rs.getString("UserName"),
                        rs.getDouble("Rating"), rs.getString("Comment"));
                reviews.add(nReview);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public void updateQRCode(Image qrImage) {
        imageView.setImage(qrImage);
    }
}