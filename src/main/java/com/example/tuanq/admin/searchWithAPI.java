package com.example.tuanq.admin;

import com.example.tuanq.ApiService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.List;

public class searchWithAPI {

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<Documents> documentTable;

    @FXML
    private TableColumn<Documents, String> authorColumn;

    @FXML
    private TableColumn<Documents, String> titleColumn;

    @FXML
    private TableColumn<Documents, String> typeColumn;

    @FXML
    private TableColumn<Documents, Integer> yearColumn;

    @FXML
    private TableColumn<Documents, String> imageColumn;

    private ObservableList<Documents> documentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("Author"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("Year"));

        imageColumn.setCellFactory(column -> new TableCell<>() {
            private final StackPane stackPane = new StackPane();
            private final ImageView imageView = new ImageView();
            private final Button addButton = new Button("Add");

            {
                imageView.setFitWidth(150);
                imageView.setFitHeight(200);
                imageView.setPreserveRatio(true);

                addButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8); -fx-font-size: 16px; -fx-border-radius: 5; -fx-background-radius: 5;");
                addButton.setVisible(false);

                stackPane.getChildren().addAll(imageView, addButton);
                setGraphic(stackPane);
                setStyle("-fx-alignment: CENTER;");

                ColorAdjust dimEffect = new ColorAdjust();
                dimEffect.setBrightness(-0.5);

                stackPane.setOnMouseEntered(event -> {
                    imageView.setEffect(dimEffect);
                    addButton.setVisible(true);
                });

                stackPane.setOnMouseExited(event -> {
                    imageView.setEffect(null);
                    addButton.setVisible(false);
                });

                addButton.setOnAction(event -> {
                    Documents document = getTableView().getItems().get(getIndex());
                    showAddConfirmation(document);
                });
            }

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);
                if (empty || imagePath == null || imagePath.equals("No Image Available")) {
                    imageView.setImage(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image(imagePath));
                    setGraphic(stackPane);
                }
            }
        });

        documentTable.setItems(documentList);
    }

    private void showAddConfirmation(Documents document) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add Document");
        alert.setHeaderText("Do you want to add this document?");
        alert.setContentText("Title: " + document.getTitle() + "\nAuthor: " + document.getAuthor());

        ButtonType confirmButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == confirmButton) {
                document.setQuantity(1);
                DocumentUtil.getInstance().insert(document);
            }
        });
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            System.err.println("Search field is empty!");
            return;
        }

        List<Documents> results = ApiService.searchDocuments(keyword);
        if (!results.isEmpty()) {
            documentList.clear();
            documentList.addAll(results);
        } else {
            System.out.println("No results found for: " + keyword);
        }
    }
}