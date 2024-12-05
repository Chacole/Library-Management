package com.example.tuanq;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class DisplayusersResearch {
    private int currentPage = 1;
    private int totalPages; // Số trang sẽ được tính dựa trên dữ liệu thực tế
    private final int rowsPerPage = 25; // Số bản ghi tối đa trên mỗi trang

    private ObservableList<Users> allUsers = FXCollections.observableArrayList();

    @FXML
    private TableView<Users> tableView;

    @FXML
    private HBox buttonBox = new HBox(10);

    @FXML
    public void initialize() {
        TableColumn<Users, Integer> IDColumn = new TableColumn<>("ID");
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Users, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn<Users, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));

        TableColumn<Users, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));

        TableColumn<Users, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));

        tableView.getColumns().addAll(IDColumn, nameColumn, emailColumn, addressColumn, phoneColumn);

        buttonBox.getChildren().clear();
    }

    public void setUsers(ArrayList<Users> users) {
        allUsers = FXCollections.observableArrayList(users);
        calculateTotalPages(); // Tính lại tổng số trang
        updateTableContent(1); // Hiển thị nội dung trang đầu tiên
        updateButtons(); // Cập nhật nút phân trang
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

    public HBox updateButtons() {
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

    private void handlePageChange(int page) {
        if (page >= 1 && page <= totalPages) {
            currentPage = page;
            updateTableContent(page);
            refreshPagination();
        }
    }

    private void refreshPagination() {
        BorderPane parent = (BorderPane) tableView.getParent();
        HBox newButtonBox = updateButtons();
        parent.setBottom(newButtonBox);
    }

    private void updateTableContent(int pageNumber) {
        int startIndex = (pageNumber - 1) * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, allUsers.size());
        tableView.setItems(FXCollections.observableArrayList(allUsers.subList(startIndex, endIndex)));
    }
}
