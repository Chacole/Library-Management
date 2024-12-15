package com.example.tuanq.admin;

import com.example.tuanq.DatabaseConnection;
import com.example.tuanq.customer.Profile;
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
import javafx.util.Callback;
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

    @FXML
    private TableColumn<Review, Void> deleteColumn;

    private double ratingNumber;

    protected int DocumentID;

    // Xử lý sự kiện nhấn nút gửi đánh giá
    @FXML
    private void handleSubmit() {
        Profile profile = Profile.getInstance();

        String comment = commentArea.getText();
        int UserID = profile.getID();
        ReviewUtil.getInstance().insert(new Review(DocumentID, UserID, " ", ratingNumber, comment));

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
        deleteColumn.setCellFactory(createButtonColumn());

        // Load data from database
        tableView.setItems(loadDocumentReviews());
    }

    private Callback<TableColumn<Review, Void>, TableCell<Review, Void>> createButtonColumn() {
        return new Callback<>() {
            @Override
            public TableCell<Review, Void> call(TableColumn<Review, Void> param) {
                return new TableCell<>() {
                    private final Button deleteButton = new Button();

                    {
                        // Thêm biểu tượng cho nút Delete
                        ImageView binIcon = new ImageView(getClass().getResource("/images/bin.png").toExternalForm());
                        binIcon.setFitWidth(20);
                        binIcon.setFitHeight(20);
                        deleteButton.setGraphic(binIcon);
                        deleteButton.setStyle("-fx-background-color: transparent;");

                        deleteButton.setOnAction(event -> {
                            Review review = getTableView().getItems().get(getIndex());
                            System.out.println("Deleting review: " + review.getUserName());

                            // Xóa review khỏi cơ sở dữ liệu
                            ReviewUtil.getInstance().delete(review);
                            getTableView().getItems().remove(review);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
            }
        };
    }

    private ObservableList<Review> loadDocumentReviews() {
        ObservableList<Review> reviews = FXCollections.observableArrayList();

        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "SELECT UserID, UserName, Rating, Comment FROM DocumentReviews WHERE DocumentID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, DocumentID);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Review nReview = new Review(DocumentID,
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

    public void setDocumentID(int ID) {
        this.DocumentID = ID;
        tableView.setItems(loadDocumentReviews());
    }
}