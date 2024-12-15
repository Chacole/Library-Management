package com.example.tuanq.admin;

import com.example.tuanq.ApiService;
import com.example.tuanq.BookQR;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class DisplaydocumentsResearch {
    private final int documentsPerRow = 4;
    private final int documentsPerPage = 12;
    private ObservableList<Documents> allDocuments;
    private int loadedDocuments = 0;

    @FXML
    private ScrollPane scrollPane;

    private GridPane documentGrid; // GridPane chứa các document

    @FXML
    public void initialize() {
        documentGrid = new GridPane();
        documentGrid.setHgap(20);
        documentGrid.setVgap(20);
        documentGrid.setAlignment(Pos.TOP_CENTER);

        documentGrid.setStyle("-fx-padding: 20 0 0 20;");

        // Gắn GridPane vào ScrollPane
        scrollPane.setContent(documentGrid);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        scrollPane.vvalueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.doubleValue() > 0.9) {
                loadMoreDocuments();
            }
        });
    }

    public void setDocuments(ArrayList<Documents> documents) {
        allDocuments = FXCollections.observableArrayList(documents);
        loadedDocuments = 0;
        documentGrid.getChildren().clear();
        loadMoreDocuments();
    }

    private void loadMoreDocuments() {
        if (allDocuments == null || loadedDocuments >= allDocuments.size()) {
            return;
        }

        int endIndex = Math.min(loadedDocuments + documentsPerPage, allDocuments.size());

        for (int i = loadedDocuments; i < endIndex; i++) {
            Documents document = allDocuments.get(i);

            ImageView imageView = new ImageView();
            try {
                String imagePath = document.getUrl();
                // Search with API
                if (imagePath == null || imagePath.isEmpty()) {
                    imagePath = ApiService.getGoogleBookImage(document.getTitle());
                }

                if (imagePath == null || imagePath.isEmpty()) {
                    imageView.setImage(new Image(getClass().getResource("/images/error.png").toExternalForm()));
                } else if (imagePath.startsWith("http") || imagePath.startsWith("https")) {
                    imageView.setImage(new Image(imagePath));
                } else {
                    imageView.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
                }
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());
            }

            imageView.setFitWidth(150);
            imageView.setFitHeight(200);
            imageView.setPreserveRatio(true);

            Button imageButton = new Button();
            imageButton.setGraphic(imageView);
            imageButton.setStyle("-fx-background-color: transparent;");
            imageButton.setOnAction(event -> {
                String qrContent = "Author: " + document.getAuthor() + "\n"
                        + "Title: " + document.getTitle() + "\n"
                        + "Type: " + document.getType() + "\n"
                        + "Year: " + document.getYear();

                ImageView qrImageView = BookQR.createQRCodeImageView(qrContent);

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/admin/DocumentReview.fxml"));
                    Parent root = loader.load();

                    DocumentReviewController controller = loader.getController();

                    controller.updateQRCode(qrImageView.getImage());

                    Stage stage = new Stage();
                    stage.setTitle("Document Review");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            });

            int row = i / documentsPerRow;
            int col = i % documentsPerRow;
            documentGrid.add(imageButton, col, row);
        }

        loadedDocuments = endIndex;
    }
}