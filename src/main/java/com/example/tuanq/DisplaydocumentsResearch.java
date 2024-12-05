package com.example.tuanq;

//package com.example.tuanq;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.control.ListView;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Priority;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Text;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
//public class Displaydocuments {
//    private int currentPage = 1;
//    private int totalPages; // Tổng số trang sẽ được tính toán
//    private final int rowsPerPage = 7; // Số bản ghi mỗi trang
//    private ObservableList<Documents> allDocuments;
//
//    @FXML
//    private ListView<Documents> listView;
//
//    @FXML
//    private HBox buttonBox = new HBox(10);
//
//    @FXML
//    public void initialize() {
//        // Lấy dữ liệu từ cơ sở dữ liệu
//        allDocuments = loadDocumentsFromDatabase();
//
//        // Tính toán tổng số trang
//        calculateTotalPages();
//
//        // Cài đặt ListView
//        listView.setCellFactory(param -> new DocumentCell());
//        updateTableContent(1);
//        updateButtons();
//    }
//
//    private ObservableList<Documents> loadDocumentsFromDatabase() {
//        ObservableList<Documents> documents = FXCollections.observableArrayList();
//        String query = "SELECT * FROM Documents";
//
//        try (Connection connection = DatabaseConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery()) {
//
//            while (resultSet.next()) {
//                Documents document = new Documents(
//                        resultSet.getString("Author"),
//                        resultSet.getString("Title"),
//                        resultSet.getString("Type"),
//                        resultSet.getInt("Year"),
//                        resultSet.getInt("Quantity"),
//                        resultSet.getString("ImagePath")
//                );
//                document.setID(resultSet.getInt("ID"));
//                documents.add(document);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return documents;
//    }
//
//    private void calculateTotalPages() {
//        if (allDocuments == null || allDocuments.isEmpty()) {
//            totalPages = 1; // Nếu không có tài liệu, mặc định 1 trang
//        } else {
//            totalPages = (int) Math.ceil((double) allDocuments.size() / rowsPerPage);
//        }
//    }
//
//    private void updateTableContent(int pageNumber) {
//        if (allDocuments == null || allDocuments.isEmpty()) {
//            listView.setItems(FXCollections.observableArrayList());
//            return;
//        }
//
//        int startIndex = (pageNumber - 1) * rowsPerPage;
//        int endIndex = Math.min(startIndex + rowsPerPage, allDocuments.size());
//
//        if (startIndex >= 0 && startIndex < allDocuments.size()) {
//            listView.setItems(FXCollections.observableArrayList(allDocuments.subList(startIndex, endIndex)));
//        }
//    }
//
//    private HBox updateButtons() {
//        buttonBox.getChildren().clear();
//
//        // Nút "Prev"
//        Button prevButton = new Button("Prev");
//        prevButton.setDisable(currentPage == 1); // Vô hiệu hóa nếu ở trang đầu tiên
//        prevButton.setOnAction(event -> {
//            if (currentPage > 1) {
//                currentPage--;
//                updateTableContent(currentPage);
//                refreshPagination();
//            }
//        });
//        buttonBox.getChildren().add(prevButton);
//
//        // Nút trang hiện tại
//        Button currentButton = new Button(String.valueOf(currentPage));
//        currentButton.setOnAction(event -> handlePageChange(currentPage));
//        buttonBox.getChildren().add(currentButton);
//
//        // Nút trang kế tiếp (nếu có)
//        if (currentPage < totalPages) {
//            Button nextPageButton = new Button(String.valueOf(currentPage + 1));
//            nextPageButton.setOnAction(event -> handlePageChange(currentPage + 1));
//            buttonBox.getChildren().add(nextPageButton);
//        }
//
//        // Nút "Next"
//        Button nextButton = new Button("Next");
//        nextButton.setDisable(currentPage == totalPages); // Vô hiệu hóa nếu ở trang cuối
//        nextButton.setOnAction(event -> {
//            if (currentPage < totalPages) {
//                currentPage++;
//                updateTableContent(currentPage);
//                refreshPagination();
//            }
//        });
//        buttonBox.getChildren().add(nextButton);
//
//        return buttonBox;
//    }
//
//    private void refreshPagination() {
//        BorderPane parent = (BorderPane) listView.getParent();
//        HBox newButtonBox = updateButtons();
//        parent.setBottom(newButtonBox);
//    }
//
//    private void handlePageChange(int page) {
//        if (page >= 1 && page <= totalPages) {
//            currentPage = page;
//            updateTableContent(page);
//            refreshPagination();
//        }
//    }
//
//    // Tùy chỉnh CellFactory
//    private static class DocumentCell extends javafx.scene.control.ListCell<Documents> {
//        @Override
//        protected void updateItem(Documents document, boolean empty) {
//            super.updateItem(document, empty);
//
//            if (empty || document == null) {
//                setGraphic(null);
//                setText(null);
//            } else {
//                // Tạo HBox để chứa ảnh và thông tin
//                HBox hBox = new HBox(10); // khoảng cách giữa các thành phần
//                hBox.setStyle("-fx-padding: 10; -fx-background-color: lightgrey; -fx-background-radius: 5;");
//
//                // Ảnh bên trái
//                ImageView imageView = new ImageView();
//                try {
//                    String imagePath = document.getUrl();
//                    if (imagePath.startsWith("http") || imagePath.startsWith("https")) {
//                        // Nếu là URL, tải ảnh từ Internet
//                        imageView = new ImageView(new Image(imagePath));
//                    } else {
//                        // Nếu là đường dẫn cục bộ
//                        imageView = new ImageView(getClass().getResource(imagePath).toExternalForm());
//                    }
//                } catch (Exception e) {
//                    System.out.println("Error loading image: " + e.getMessage());
//                }
//
//                // Thiết lập kích thước cho ảnh
//                imageView.setFitWidth(90);
//                imageView.setFitHeight(120);
//
//                // Thông tin sách bên phải
//                VBox vBox = new VBox(5); // khoảng cách giữa các dòng thông tin
//                Text ID = new Text("ID: " + document.getID());
//                Text author = new Text("Author: " + document.getAuthor());
//                Text title = new Text("Title: " + document.getTitle());
//                Text type = new Text("Type: " + document.getType());
//                Text year = new Text("Year: " + document.getYear());
//                Text quantity = new Text("Quantity: " + document.getQuantity());
//
//                vBox.getChildren().addAll(ID, author, title, type, year, quantity);
//
//                // Nút Borrow
//                Button buttonBorrow = new Button("Borrow");
//                buttonBorrow.setOnAction(event -> {
//                    // Hiển thị Dialog với thông tin Borrow
//                    InformationBorrower informationBorrower = new InformationBorrower();
//                    informationBorrower.showDetailBorrowing();
//                });
//
//                buttonBorrow.setPrefWidth(100); // Đặt chiều rộng
//                buttonBorrow.setPrefHeight(30); // Đặt chiều cao
//
//                HBox buttonContainer = new HBox(buttonBorrow);
//                buttonContainer.setAlignment(Pos.CENTER_RIGHT); // Căn phải
//                HBox.setHgrow(buttonContainer, Priority.ALWAYS); // Đẩy về góc phải
//
//                // Thêm ảnh và thông tin vào HBox
//                hBox.getChildren().addAll(imageView, vBox, buttonContainer);
//
//                HBox.setHgrow(vBox, Priority.ALWAYS);
//
//                // Gán HBox làm giao diện cho cell
//                setGraphic(hBox);
//            }
//        }
//    }
//}

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class DisplaydocumentsResearch {
    private int currentPage = 1;
    private int totalPages; // Tổng số trang sẽ được tính toán
    private final int rowsPerPage = 9; // Số bản ghi mỗi trang
    private ObservableList<Documents> allDocuments;

    @FXML
    private ListView<Documents> listView;

    @FXML
    private HBox buttonBox = new HBox(10);

    @FXML
    public void initialize() {
        listView.setCellFactory(param -> new DocumentCell());
    }

    public void setDocuments(ArrayList<Documents> documents) {
        allDocuments = FXCollections.observableArrayList(documents); // Chuyển đổi sang ObservableList
        calculateTotalPages(); // Tính lại tổng số trang
        updateTableContent(1); // Hiển thị nội dung trang đầu tiên
        updateButtons(); // Cập nhật nút phân trang
    }

    private void calculateTotalPages() {
        if (allDocuments == null || allDocuments.isEmpty()) {
            totalPages = 1; // Nếu không có tài liệu, mặc định 1 trang
        } else {
            totalPages = (int) Math.ceil((double) allDocuments.size() / rowsPerPage);
        }
    }

    private void updateTableContent(int pageNumber) {
        if (allDocuments == null || allDocuments.isEmpty()) {
            listView.setItems(FXCollections.observableArrayList());
            return;
        }

        int startIndex = (pageNumber - 1) * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, allDocuments.size());

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
                HBox hBox = new HBox(10);
                hBox.setStyle("-fx-padding: 10; -fx-background-color: lightgrey; -fx-background-radius: 5;");

                ImageView imageView = new ImageView();
                try {
                    String imagePath = document.getUrl();
                    if (imagePath.startsWith("http") || imagePath.startsWith("https")) {
                        imageView = new ImageView(new Image(imagePath));
                    } else {
                        imageView = new ImageView(getClass().getResource(imagePath).toExternalForm());
                    }
                } catch (Exception e) {
                    System.out.println("Error loading image: " + e.getMessage());
                }

                imageView.setFitWidth(90);
                imageView.setFitHeight(120);

                VBox vBox = new VBox(5);
                Text ID = new Text("ID: " + document.getID());
                Text author = new Text("Author: " + document.getAuthor());
                Text title = new Text("Title: " + document.getTitle());
                Text type = new Text("Type: " + document.getType());
                Text year = new Text("Year: " + document.getYear());
                Text quantity = new Text("Quantity: " + document.getQuantity());

                vBox.getChildren().addAll(ID, author, title, type, year, quantity);

                Button buttonBorrow = new Button("Borrow");
                buttonBorrow.setOnAction(event -> {
                    // Tạo Dialog
                    Dialog<BorrowRecord> dialog = new Dialog<>();
                    dialog.setTitle("User Information Form");
                    dialog.setHeaderText("Please enter your details below:");

                    // Nút Submit và Cancel
                    ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

                    Button submitButton = (Button) dialog.getDialogPane().lookupButton(submitButtonType);
                    submitButton.setDisable(true);

                    TextField nameField = new TextField();
                    nameField.setPromptText("userName");

                    DatePicker borrowPicker = new DatePicker();
                    borrowPicker.setPromptText("Date of Borrowing");

                    DatePicker returnPicker = new DatePicker();
                    returnPicker.setPromptText("Date of Returning");

                    submitButton.disableProperty().bind(
                            Bindings.createBooleanBinding(() ->
                                            nameField.getText().trim().isEmpty() ||
                                                    borrowPicker.getValue() == null ||
                                                    returnPicker.getValue() == null,
                                    nameField.textProperty(),
                                    borrowPicker.valueProperty(),
                                    returnPicker.valueProperty()
                            )
                    );

                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(10);

                    grid.add(new Label("Name:"), 0, 0);
                    grid.add(nameField, 1, 0);
                    grid.add(new Label("Date of Borrowing:"), 0, 1);
                    grid.add(borrowPicker, 1, 1);
                    grid.add(new Label("Date of Returning:"), 0, 2);
                    grid.add(returnPicker, 1, 2);

                    GridPane.setHgrow(nameField, Priority.ALWAYS);
                    GridPane.setHgrow(borrowPicker, Priority.ALWAYS);
                    GridPane.setHgrow(returnPicker, Priority.ALWAYS);

                    dialog.getDialogPane().setContent(grid);

                    // Xử lý khi nhấn nút Submit
                    dialog.setResultConverter(dialogButton -> {
                        if (dialogButton == submitButtonType) {
                            String userName = nameField.getText();
                            LocalDate borrowDate = borrowPicker.getValue();
                            LocalDate returnDate = returnPicker.getValue();

                            // Chuyển đổi LocalDate thành java.util.Date
                            Date borrowDateConverted = java.util.Date.from(borrowDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
                            Date returnDateConverted = java.util.Date.from(returnDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());

                            // Trả về đối tượng BorrowRecord
                            return new BorrowRecord(userName, document.getTitle(), borrowDateConverted, returnDateConverted);
                        }
                        return null; // Trả về null nếu nhấn Cancel
                    });

                    // Hiển thị dialog và nhận kết quả
                    dialog.showAndWait().ifPresent(result -> {
                        System.out.println("User Name: " + result.getUserName());
                        System.out.println("Document Title: " + result.getDocumentTitle());
                        System.out.println("Borrow Date: " + result.getFormattedBorrowDate());
                        System.out.println("Return Date: " + result.getFormattedReturnDate());

                        boolean r = new RecordUtil().addBorrowRecord(result);
                        if (r) {
                            System.out.println("Borrow record đã được thêm thành công.");
                        } else {
                            System.out.println("Thêm borrow record thất bại.");
                        }
                    });
                });

                buttonBorrow.setPrefWidth(100);
                buttonBorrow.setPrefHeight(30);

                HBox buttonContainer = new HBox(buttonBorrow);
                buttonContainer.setAlignment(Pos.CENTER_RIGHT);
                HBox.setHgrow(buttonContainer, Priority.ALWAYS);

                hBox.getChildren().addAll(imageView, vBox, buttonContainer);
                HBox.setHgrow(vBox, Priority.ALWAYS);
                setGraphic(hBox);
            }
        }
    }

    public ListView<Documents> getListView() {
        return listView;
    }
}