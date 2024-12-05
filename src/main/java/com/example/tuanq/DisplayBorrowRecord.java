package com.example.tuanq;

import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class DisplayBorrowRecord {
    private int currentPage = 1;
    private int totalPages; // Tổng số trang
    private final int rowsPerPage = 7; // Số bản ghi mỗi trang
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
        titleColumn.setMinWidth(200);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("documentTitle"));

        TableColumn<BorrowRecord, String> borrowColumn = new TableColumn<>("Borrow Day");
        borrowColumn.setMinWidth(150);
        borrowColumn.setCellValueFactory(new PropertyValueFactory<>("formattedBorrowDate"));

        TableColumn<BorrowRecord, String> returnColumn = new TableColumn<>("Return Day");
        returnColumn.setMinWidth(150);
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

        imageColumn.setCellFactory(column -> new TableCell<BorrowRecord, ImageView>() {
            @Override
            protected void updateItem(ImageView item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    StackPane stackPane = new StackPane();

                    Label hoverLabel = new Label("Return");
                    hoverLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-opacity: 0;");
                    hoverLabel.setMouseTransparent(true); // Không nhận sự kiện chuột

                    item.setStyle("-fx-opacity: 1;");

                    // Di chuột vào
                    stackPane.setOnMouseEntered(event -> {
                        item.setStyle("-fx-opacity: 0.5;");
                        hoverLabel.setStyle("-fx-opacity: 1;");
                    });

                    // Di chuột ra
                    stackPane.setOnMouseExited(event -> {
                        item.setStyle("-fx-opacity: 1;");
                        hoverLabel.setStyle("-fx-opacity: 0;");
                    });

                    // Ghi đè button
                    Button imageButton = new Button();
                    imageButton.setGraphic(item);
                    imageButton.setStyle("-fx-background-color: transparent;");
                    imageButton.setOnAction(event -> {
                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.setTitle("Returning...");
                        dialog.setHeaderText("Do you confirm returning this document?");

                        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
                        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

                        Optional<ButtonType> result = dialog.showAndWait();

                        if (result.isPresent() && result.get() == submitButtonType) {
                            System.out.println("Admin confirmed the action!");
                            BorrowRecord record = getTableView().getItems().get(getIndex());
                            RecordUtil recordUtil = new RecordUtil();
                            recordUtil.deleteBorrowRecord(record);
                        }

                    });

                    stackPane.getChildren().addAll(imageButton, hoverLabel);
                    StackPane.setAlignment(hoverLabel, Pos.CENTER);

                    setGraphic(stackPane);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        // Thêm các cột vào bảng
        table.getColumns().addAll(nameColumn, titleColumn, borrowColumn, returnColumn, imageColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Lấy dữ liệu từ database
        loadBorrowRecordsFromDatabase();

        // Hiển thị dữ liệu của trang đầu tiên
        updateTableContent(table, 1);

        // Cập nhật các nút phân trang
        HBox buttonBox = updateButtons(table);

        // Thêm bảng và các nút vào BorderPane
        borderPane.setCenter(table);
        borderPane.setBottom(buttonBox);

        return borderPane;
    }

    private void loadBorrowRecordsFromDatabase() {
        allBorrowRecord = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT b.documentID, b.userID, u.Name AS userName, d.Title AS documentTitle, " +
                    "b.borrowDate, b.returnDate, d.ImagePath AS image " +
                    "FROM BorrowRecord b " +
                    "JOIN Users u ON b.userID = u.ID " +
                    "JOIN Documents d ON b.documentID = d.ID";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String userName = resultSet.getString("userName");
                String documentTitle = resultSet.getString("documentTitle");
                Date borrowDate = resultSet.getDate("borrowDate");
                Date returnDate = resultSet.getDate("returnDate");
                String image = resultSet.getString("image");

                BorrowRecord record = new BorrowRecord(userName, documentTitle, borrowDate, returnDate, image);
                allBorrowRecord.add(record);
            }

            totalPages = (int) Math.ceil(allBorrowRecord.size() / (double) rowsPerPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                refreshButtons(table, buttonBox);
            }
        });
        buttonBox.getChildren().add(prevButton);

        // Nút trang hiện tại
        Button currentButton = new Button(String.valueOf(currentPage));
        currentButton.setOnAction(event -> {
            updateTableContent(table, currentPage);
            refreshButtons(table, buttonBox);
        });
        buttonBox.getChildren().add(currentButton);

        // Nút trang kế tiếp (nếu có)
        if (currentPage < totalPages) {
            Button nextPageButton = new Button(String.valueOf(currentPage + 1));
            nextPageButton.setOnAction(event -> {
                if (currentPage < totalPages) {
                    currentPage++;
                    updateTableContent(table, currentPage);
                    refreshButtons(table, buttonBox);
                }
            });
            buttonBox.getChildren().add(nextPageButton);
        }

        // Nút "Next"
        Button nextButton = new Button("Next");
        nextButton.setDisable(currentPage == totalPages); // Vô hiệu hóa nếu ở trang cuối
        nextButton.setOnAction(event -> {
            if (currentPage < totalPages) {
                currentPage++;
                updateTableContent(table, currentPage);
                refreshButtons(table, buttonBox);
            }
        });
        buttonBox.getChildren().add(nextButton);

        return buttonBox;
    }

    private void refreshButtons(TableView<BorrowRecord> table, HBox buttonBox) {
        BorderPane parent = (BorderPane) table.getParent();
        HBox newButtonBox = updateButtons(table);
        parent.setBottom(newButtonBox);
    }

    private void updateTableContent(TableView<BorrowRecord> table, int pageNumber) {
        int startIndex = (pageNumber - 1) * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, allBorrowRecord.size());
        table.setItems(FXCollections.observableArrayList(allBorrowRecord.subList(startIndex, endIndex)));
    }

    private <T> void centerColumn(TableColumn<BorrowRecord, T> column) {
        column.setCellFactory(col -> new TableCell<BorrowRecord, T>() {
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
        });
    }


}