package com.example.tuanq.admin;

import com.example.tuanq.*;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Date;

public class Displaydocuments {
    private int currentPage = 1;
    private int totalPages;
    private final int rowsPerPage = 9;
    private ObservableList<Documents> allDocuments;

    @FXML
    private ListView<Documents> listView;

    @FXML
    private HBox buttonBox = new HBox(10);

    @FXML
    public void initialize() {
        // Tải dữ liệu từ cơ sở dữ liệu trong một luồng nền
        // Sử dụng DocumentDataLoader để tải dữ liệu
        DocumentDataLoader dataLoader = new DocumentDataLoader();
        Task<ObservableList<Documents>> loadTask = dataLoader.createLoadTask();

        // Ẩn trạng thái khi Task hoàn thành
        loadTask.setOnSucceeded(event -> {
            allDocuments = loadTask.getValue();
            calculateTotalPages();
            listView.setCellFactory(param -> new DocumentCell());
            updateTableContent(1); // Hiển thị trang đầu tiên
            updateButtons();
        });

        loadTask.setOnFailed(event -> {
            System.err.println("Failed to load documents: " + loadTask.getException().getMessage());
        });

        // Chạy Task trong luồng nền
        Thread backgroundThread = new Thread(loadTask);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
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
    private static class DocumentCell extends ListCell<Documents> {
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

                // Ghi đè button
                Button imageButton = new Button();
                imageButton.setGraphic(imageView);
                imageButton.setStyle("-fx-background-color: transparent;");
                imageButton.setOnAction(event -> {
                    // Hiển thị QR code
                    String qrContent = "Author: " + document.getAuthor() + "\n"
                            + "Title: " + document.getTitle() + "\n"
                            + "Type: " + document.getType() + "\n"
                            + "Year: " + document.getYear();

                    ImageView qrImageView = BookQR.createQRCodeImageView(qrContent);

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/admin/DocumentReview.fxml"));
                        Parent root = loader.load();

                        // Lấy controller từ FXML
                        DocumentReviewController controller = loader.getController();

                        // Thay thế ảnh QR trong giao diện
                        controller.updateQRCode(qrImageView.getImage());

                        // Hiển thị cửa sổ mới
                        Stage stage = new Stage();
                        stage.setTitle("Document Review");
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                });

                VBox vBox = new VBox(5);
                Text author = new Text("Author: " + document.getAuthor());
                author.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                author.setFill(javafx.scene.paint.Color.DARKBLUE);

                Text title = new Text("Title: " + document.getTitle());
                title.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
                title.setFill(javafx.scene.paint.Color.BLACK);

                Text type = new Text("Type: " + document.getType());
                type.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
                type.setFill(javafx.scene.paint.Color.BLACK);

                Text year = new Text("Year: " + document.getYear());
                year.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
                year.setFill(javafx.scene.paint.Color.BLACK);

                Text quantity = new Text("Quantity: " + document.getQuantity());
                quantity.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
                quantity.setFill(javafx.scene.paint.Color.BLACK);

                vBox.getChildren().addAll(author, title, type, year, quantity);

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
                            Date borrowDateConverted = Date.from(borrowDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
                            Date returnDateConverted = Date.from(returnDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());

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

                hBox.getChildren().addAll(imageButton, vBox, buttonContainer);
                HBox.setHgrow(vBox, Priority.ALWAYS);
                setGraphic(hBox);
            }
        }
    }

    public ListView<Documents> getListView() {
        return listView;
    }
}