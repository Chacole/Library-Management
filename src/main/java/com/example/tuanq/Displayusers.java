package com.example.tuanq;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Displayusers {

    private int currentPage = 1;
    private final int totalPages = 4;
    private ObservableList<Users> allUsers;

    public BorderPane createUserTable() {
        BorderPane borderPane = new BorderPane();

        // Tạo bảng TableView
        TableView<Users> table = new TableView<>();
        table.setPrefHeight(700);

        // Tạo các cột
        TableColumn<Users, Integer> IDColumn = new TableColumn<>("ID");
        IDColumn.setMinWidth(50);
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Users, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn<Users, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setMinWidth(200);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));

        TableColumn<Users, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setMinWidth(200);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));

        TableColumn<Users, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setMinWidth(200);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));

        // Thêm các cột vào bảng
        table.getColumns().addAll(IDColumn, nameColumn, emailColumn, addressColumn, phoneColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Dữ liệu tổng hợp
        ManagementID idManager = new ManagementID();
        allUsers = FXCollections.observableArrayList();
        for (int i = 1; i <= 16; i++) {
            Users user = new Users("User" + i, "user" + i + "@example.com", "Address " + i, "Phone " + i);
            user.assignID(idManager);
            allUsers.add(user);
        }

        // Hiển thị dữ liệu của trang đầu tiên
        updateTableContent(table, 1);

        // Cập nhật các nút phân trang
        HBox buttonBox = updateButtons(table);

        // Thêm bảng và các nút vào BorderPane
        borderPane.setCenter(table);
        borderPane.setBottom(buttonBox);

        return borderPane;
    }

    public HBox updateButtons(TableView<Users> table) {
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().clear();

        // Nút "Prev"
        Button prevButton = new Button("Prev");
        prevButton.setDisable(currentPage == 1); // Vô hiệu hóa nếu ở trang đầu tiên
        prevButton.setOnAction(event -> {
            if (currentPage > 1) {
                currentPage--;
                updateTableContent(table, currentPage);
                refreshPagination(table);
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
                refreshPagination(table);
            }
        });
        buttonBox.getChildren().add(nextButton);

        return buttonBox;
    }

    private void handlePageChange(TableView<Users> table, int page) {
        if (page >= 1 && page <= totalPages) {
            currentPage = page;
            updateTableContent(table, page);
            refreshPagination(table);
        }
    }

    private void refreshPagination(TableView<Users> table) {
        BorderPane parent = (BorderPane) table.getParent();
        HBox newButtonBox = updateButtons(table);
        parent.setBottom(newButtonBox);
    }

    private void updateTableContent(TableView<Users> table, int pageNumber) {
        int startIndex = (pageNumber - 1) * 5;
        int endIndex = Math.min(startIndex + 5, allUsers.size());
        table.setItems(FXCollections.observableArrayList(allUsers.subList(startIndex, endIndex)));
    }
}