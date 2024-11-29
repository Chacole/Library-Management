package com.example.tuanq;

import com.example.tuanq.ExampleValues.DocumentFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class Displaydocuments {
    private int currentPage = 1;
    private final int totalPages = 2;
    private ObservableList<Documents> allDocuments;

    @FXML
    private ListView<Documents> listView;

    @FXML
    private HBox buttonBox = new HBox(10);

    @FXML
    public void initialize() {
        // Tạo danh sách dữ liệu
        allDocuments = DocumentFactory.createDocuments();

        // Cài đặt ListView
        listView.setCellFactory(param -> new DocumentCell());
        updateTableContent(1);
        updateButtons();
    }

    private void updateTableContent(int pageNumber) {
        if (allDocuments == null || allDocuments.isEmpty()) {
            listView.setItems(FXCollections.observableArrayList());
            return;
        }

        int startIndex = (pageNumber - 1) * 5;
        int endIndex = Math.min(startIndex + 5, allDocuments.size());

        if (startIndex >= 0 && startIndex < allDocuments.size()) {
            listView.setItems(FXCollections.observableArrayList(allDocuments.subList(startIndex, endIndex)));
        }
    }

    private HBox updateButtons() {
        buttonBox.getChildren().clear();

        // Nút "Prev"
        Button prevButton = new Button("Prev");
        prevButton.setDisable(currentPage == 1); // Vô hiệu hóa nếu ở trang đầu tiên
        prevButton.setOnAction(event -> {
            if (currentPage > 1) {
                currentPage--;
                updateTableContent(currentPage);
                refreshPagination();
            }
        });
        buttonBox.getChildren().add(prevButton);

        // Nút trang hiện tại
        Button currentButton = new Button(String.valueOf(currentPage));
        currentButton.setOnAction(event -> handlePageChange(currentPage));
        buttonBox.getChildren().add(currentButton);

        // Nút trang kế tiếp (nếu có)
        if (currentPage < totalPages) {
            Button nextPageButton = new Button(String.valueOf(currentPage + 1));
            nextPageButton.setOnAction(event -> handlePageChange(currentPage + 1));
            buttonBox.getChildren().add(nextPageButton);
        }

        // Nút "Next"
        Button nextButton = new Button("Next");
        nextButton.setDisable(currentPage == totalPages); // Vô hiệu hóa nếu ở trang cuối
        nextButton.setOnAction(event -> {
            if (currentPage < totalPages) {
                currentPage++;
                updateTableContent(currentPage);
                refreshPagination();
            }
        });
        buttonBox.getChildren().add(nextButton);

        return buttonBox;
    }

    private void refreshPagination() {
        BorderPane parent = (BorderPane) listView.getParent();
        HBox newButtonBox = updateButtons();
        parent.setBottom(newButtonBox);
    }

    private void handlePageChange(int page) {
        if (page >= 1 && page <= totalPages) {
            currentPage = page;
            updateTableContent(page);
            refreshPagination();
        }
    }

    // Tùy chỉnh CellFactory
    private static class DocumentCell extends javafx.scene.control.ListCell<Documents> {
        @Override
        protected void updateItem(Documents document, boolean empty) {
            super.updateItem(document, empty);

            if (empty || document == null) {
                setGraphic(null);
                setText(null);
            } else {
                // Tạo HBox để chứa ảnh và thông tin
                HBox hBox = new HBox(10); // khoảng cách giữa các thành phần
                hBox.setStyle("-fx-padding: 10; -fx-background-color: lightgrey; -fx-background-radius: 5;");

                // Ảnh bên trái
                ImageView imageView = new ImageView();
                try {
                    // Ảnh
                    imageView = new ImageView(getClass().getResource(document.getUrl()).toExternalForm());
                } catch (Exception e) {
                    System.out.println("Error loading image: " + e.getMessage());
                }

                // Thiết lập kích thước cho ảnh
                imageView.setFitWidth(90);
                imageView.setFitHeight(120);

                // Thông tin sách bên phải
                VBox vBox = new VBox(5); // khoảng cách giữa các dòng thông tin
                Text ID = new Text("ID: " + document.getID());
                Text author = new Text("Author: " + document.getAuthor());
                Text title = new Text("Title: " + document.getTitle());
                Text type = new Text("Type: " + document.getType());
                Text year = new Text("Year: " + document.getYear());
                Text quantity = new Text("Quantity: " + document.getQuantity());

                vBox.getChildren().addAll(ID, author, title, type, year, quantity);

                // Nút Borrow
                Button buttonBorrow = new Button("Borrow");
                buttonBorrow.setOnAction(event -> {
                    // Hiển thị Dialog với thông tin Borrow
                    InformationBorrower informationBorrower = new InformationBorrower();
                    informationBorrower.showDetailBorrowing();
                });

                buttonBorrow.setPrefWidth(100); // Đặt chiều rộng
                buttonBorrow.setPrefHeight(30); // Đặt chiều cao


                HBox buttonContainer = new HBox(buttonBorrow);
                buttonContainer.setAlignment(Pos.CENTER_RIGHT); // Căn phải
                HBox.setHgrow(buttonContainer, Priority.ALWAYS); // Đẩy về góc phải

                // Thêm ảnh và thông tin vào HBox
                hBox.getChildren().addAll(imageView, vBox, buttonContainer);

                HBox.setHgrow(vBox, Priority.ALWAYS);

                // Gán HBox làm giao diện cho cell
                setGraphic(hBox);
            }
        }
    }
}

