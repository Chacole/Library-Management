package com.example.tuanq;

import com.example.tuanq.ExampleValues.BorrowFactory;

import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TableCell;
import javafx.geometry.Pos;

public class DisplayBorrowRecord {
    private int currentPage = 1;
    private final int totalPages = 2;
    private ObservableList<BorrowRecord> allBorrowRecord;

    public BorderPane createBorrowRecordTable() {
        BorderPane borderPane = new BorderPane();

        // Tạo bảng TableView
        TableView<BorrowRecord> table = new TableView<>();
        table.setPrefHeight(700);

        // Tạo các cột
        TableColumn<BorrowRecord, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<BorrowRecord, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setMinWidth(100);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("documentTitle"));

        TableColumn<BorrowRecord, String> borrowColumn = new TableColumn<>("Borrow Day");
        borrowColumn.setMinWidth(200);
        borrowColumn.setCellValueFactory(new PropertyValueFactory<>("formattedBorrowDate"));

        TableColumn<BorrowRecord, String> returnColumn = new TableColumn<>("Return Day");
        returnColumn.setMinWidth(200);
        returnColumn.setCellValueFactory(new PropertyValueFactory<>("formattedReturnDate"));

        TableColumn<BorrowRecord, ImageView> imageColumn = new TableColumn<>("Book");
        imageColumn.setMinWidth(200);
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imageView"));

        // Căn giữa tất cả các giá trị trong các cột
        centerColumn(nameColumn);
        centerColumn(titleColumn);
        centerColumn(borrowColumn);
        centerColumn(returnColumn);

        imageColumn.setCellFactory(column -> new TableCell<BorrowRecord, ImageView>() {
            @Override
            protected void updateItem(ImageView item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setGraphic(item);
                    setAlignment(Pos.CENTER);
                }
            }
        });


        // Thêm các cột vào bảng
        table.getColumns().addAll(nameColumn, titleColumn, borrowColumn, returnColumn, imageColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Lấy dữ liệu từ BorrowFactory
        allBorrowRecord = BorrowFactory.createBorrowRecords();

        // Hiển thị dữ liệu của trang đầu tiên
        updateTableContent(table, 1);

        // Cập nhật các nút phân trang
        HBox buttonBox = updateButtons(table);

        // Thêm bảng và các nút vào BorderPane
        borderPane.setCenter(table);
        borderPane.setBottom(buttonBox);

        return borderPane;
    }

    private HBox updateButtons(TableView<BorrowRecord> table) {
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().clear();

        // Nút "Prev"
        Button prevButton = new Button("Prev");
        prevButton.setDisable(currentPage == 1); // Vô hiệu hóa nếu ở trang đầu tiên
        prevButton.setOnAction(event -> {
            if (currentPage > 1) {
                currentPage--;
                updateTableContent(table, currentPage);
            }
        });
        buttonBox.getChildren().add(prevButton);

        // Nút trang hiện tại
        Button currentButton = new Button(String.valueOf(currentPage));
        currentButton.setOnAction(event -> handlePageChange(table, currentPage));
        buttonBox.getChildren().add(currentButton);

        // Nút trang kế tiếp (nếu có)
        if (currentPage < totalPages) {
            Button nextPageButton = new Button(String.valueOf(currentPage + 1));
            nextPageButton.setOnAction(event -> handlePageChange(table, currentPage + 1));
            buttonBox.getChildren().add(nextPageButton);
        }

        // Nút "Next"
        Button nextButton = new Button("Next");
        nextButton.setDisable(currentPage == totalPages); // Vô hiệu hóa nếu ở trang cuối
        nextButton.setOnAction(event -> {
            if (currentPage < totalPages) {
                currentPage++;
                updateTableContent(table, currentPage);
            }
        });
        buttonBox.getChildren().add(nextButton);

        return buttonBox;
    }

    private void handlePageChange(TableView<BorrowRecord> table, int page) {
        if (page >= 1 && page <= totalPages) {
            currentPage = page;
            updateTableContent(table, page);
        }
    }

    private void updateTableContent(TableView<BorrowRecord> table, int pageNumber) {
        int startIndex = (pageNumber - 1) * 3;
        int endIndex = Math.min(startIndex + 3, allBorrowRecord.size());
        table.setItems(FXCollections.observableArrayList(allBorrowRecord.subList(startIndex, endIndex)));
    }

    // Phương thức giúp căn giữa cột
    private <T> void centerColumn(TableColumn<BorrowRecord, T> column) {
        column.setCellFactory(col -> {
            return new TableCell<BorrowRecord, T>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(item.toString());
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });
    }
}