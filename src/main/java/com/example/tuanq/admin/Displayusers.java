package com.example.tuanq.admin;

import com.example.tuanq.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Displayusers {

    private int currentPage = 1;
    private int totalPages; // Số trang sẽ được tính dựa trên dữ liệu thực tế
    private final int rowsPerPage = 27; // Số bản ghi tối đa trên mỗi trang
    private ObservableList<Users> allUsers = FXCollections.observableArrayList();

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

        // Lấy dữ liệu từ database
        loadUsersFromDatabase();

        // Tính toán tổng số trang
        calculateTotalPages();

        // Hiển thị dữ liệu của trang đầu tiên
        updateTableContent(table, 1);

        // Cập nhật các nút phân trang
        HBox buttonBox = updateButtons(table);

        // Thêm bảng và các nút vào BorderPane
        borderPane.setCenter(table);
        borderPane.setBottom(buttonBox);

        return borderPane;
    }

    /**
     * Truy vấn dữ liệu từ database và thêm vào allUsers.
     */
    private void loadUsersFromDatabase() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Users";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Xóa dữ liệu cũ
            allUsers.clear();

            // Duyệt qua các bản ghi và thêm vào danh sách
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String email = resultSet.getString("Email");
                String address = resultSet.getString("Address");
                String phone = resultSet.getString("Phone");

                Users user = new Users(name, email, address, phone);
                user.setID(id); // Gán ID từ database
                allUsers.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tính toán tổng số trang dựa trên số bản ghi và số dòng mỗi trang.
     */
    private void calculateTotalPages() {
        if (allUsers.isEmpty()) {
            totalPages = 1; // Ít nhất phải có 1 trang ngay cả khi không có dữ liệu
        } else {
            totalPages = (int) Math.ceil((double) allUsers.size() / rowsPerPage);
        }
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
        int startIndex = (pageNumber - 1) * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, allUsers.size());
        table.setItems(FXCollections.observableArrayList(allUsers.subList(startIndex, endIndex)));
    }
}