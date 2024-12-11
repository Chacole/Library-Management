package com.example.tuanq.customer;

import com.example.tuanq.*;

import com.example.tuanq.admin.*;
import com.example.tuanq.admin.DocumentReviewController;
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
import java.util.ArrayList;
import java.util.Date;

public class Displaydocuments {
    private int currentPage = 1;
    private int totalPages;
    private final int rowsPerPage = 9;
    private ObservableList<Documents> allDocuments;

    @FXML
    private Button buttonResearch;

    @FXML
    private ListView<Documents> listView;

    @FXML
    private HBox buttonBox = new HBox(10);

    @FXML
    public void initialize() {
        buttonResearch.setOnAction(event -> handleButtonResearch());

        // Tải dữ liệu từ cơ sở dữ liệu trong một luồng nền
        // Sử dụng DocumentDataLoader để tải dữ liệu
        DocumentDataLoader dataLoader = new DocumentDataLoader();
        Task<ObservableList<Documents>> loadTask = dataLoader.createLoadTask();

        loadTask.setOnSucceeded(event -> {
            allDocuments = loadTask.getValue();
            calculateTotalPages();
            listView.setCellFactory(param -> new Displaydocuments.DocumentCell());
            updateTableContent(1); // Hiển thị trang đầu tiên
            updateButtons();
        });

        loadTask.setOnFailed(event -> {
            System.err.println("Failed to load documents: " + loadTask.getException().getMessage());
        });

        // Chạy Task trong luồng nền
        MultiThread.getExecutorService().submit(loadTask);
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
        prevButton.setDisable(currentPage == 1);
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


    public ListView<Documents> getListView() {
        return listView;
    }

    @FXML
    private void handleButtonResearch() {
        // Tạo Dialog cho tìm kiếm
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Research Document Form");
        dialog.setHeaderText("Enter search criteria:");

        dialog.getDialogPane().setPrefSize(400, 500);

        // Tạo các trường nhập liệu
        TextField authorField = new TextField();
        authorField.setPromptText("Author");

        TextField titleField = new TextField();
        titleField.setPromptText("Title");

        TextField typeField = new TextField();
        typeField.setPromptText("Type");

        TextField yearPublishedField = new TextField();
        yearPublishedField.setPromptText("Year Published");
        yearPublishedField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                yearPublishedField.setText(oldValue); // Chỉ cho phép nhập số
            }
        });

        // Sắp xếp giao diện các trường nhập liệu trong GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(20);

        grid.add(new Label("Author:"), 0, 0);
        grid.add(authorField, 1, 0);

        grid.add(new Label("Title:"), 0, 1);
        grid.add(titleField, 1, 1);

        grid.add(new Label("Type:"), 0, 2);
        grid.add(typeField, 1, 2);

        grid.add(new Label("Year:"), 0, 3);
        grid.add(yearPublishedField, 1, 3);

        GridPane.setHgrow(authorField, Priority.ALWAYS);
        GridPane.setHgrow(titleField, Priority.ALWAYS);
        GridPane.setHgrow(typeField, Priority.ALWAYS);
        GridPane.setHgrow(yearPublishedField, Priority.ALWAYS);

        dialog.getDialogPane().setContent(grid);

        // Xử lý kết quả tìm kiếm ngay lập tức khi người dùng nhập
        authorField.textProperty().addListener((observable, oldValue, newValue) -> performSearch(authorField, titleField, typeField, yearPublishedField));
        titleField.textProperty().addListener((observable, oldValue, newValue) -> performSearch(authorField, titleField, typeField, yearPublishedField));
        typeField.textProperty().addListener((observable, oldValue, newValue) -> performSearch(authorField, titleField, typeField, yearPublishedField));
        yearPublishedField.textProperty().addListener((observable, oldValue, newValue) -> performSearch(authorField, titleField, typeField, yearPublishedField));

        // Hiển thị Dialog
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.show();
    }

    /**
     * Hàm thực hiện tìm kiếm dựa trên tiêu chí
     */
    private void performSearch(TextField authorField, TextField titleField, TextField typeField, TextField yearPublishedField) {
        String author = authorField.getText().trim();
        String title = titleField.getText().trim();
        String type = typeField.getText().trim();
        int year = yearPublishedField.getText().trim().isEmpty() ? -1 : Integer.parseInt(yearPublishedField.getText().trim());

        // Tạo đối tượng chứa các tiêu chí tìm kiếm
        Documents searchCriteria = new Documents(author, title, type, year, 0);

        // Gọi DocumentUtil để tìm kiếm
        DocumentUtil documentUtil = new DocumentUtil();
        ArrayList<Documents> documents = documentUtil.select(searchCriteria);

        // Hiển thị kết quả
        if (documents == null || documents.isEmpty()) {
            System.out.println("No documents found");
            listView.setItems(FXCollections.observableArrayList()); // Không tìm thấy kết quả
        } else {
            listView.setItems(FXCollections.observableArrayList(documents)); // Hiển thị danh sách tài liệu
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
                    Profile profile = Profile.getInstance();
                    String userName = profile.getUsername();

                    // Tạo Dialog
                    Dialog<BorrowRecord> dialog = new Dialog<>();
                    dialog.setTitle("User Information Form");
                    dialog.setHeaderText("Please enter your details below:");

                    // Nút Submit và Cancel
                    ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

                    Button submitButton = (Button) dialog.getDialogPane().lookupButton(submitButtonType);
                    submitButton.setDisable(true);

                    DatePicker borrowPicker = new DatePicker();
                    borrowPicker.setPromptText("Date of Borrowing");

                    DatePicker returnPicker = new DatePicker();
                    returnPicker.setPromptText("Date of Returning");

                    submitButton.disableProperty().bind(
                            Bindings.createBooleanBinding(() ->
                                                    borrowPicker.getValue() == null ||
                                                    returnPicker.getValue() == null,
                                    borrowPicker.valueProperty(),
                                    returnPicker.valueProperty()
                            )
                    );

                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(10);

                    grid.add(new Label("Date of Borrowing:"), 0, 0);
                    grid.add(borrowPicker, 1, 0);
                    grid.add(new Label("Date of Returning:"), 0, 1);
                    grid.add(returnPicker, 1, 1);

                    GridPane.setHgrow(borrowPicker, Priority.ALWAYS);
                    GridPane.setHgrow(returnPicker, Priority.ALWAYS);

                    dialog.getDialogPane().setContent(grid);

                    // Xử lý khi nhấn nút Submit
                    dialog.setResultConverter(dialogButton -> {
                        if (dialogButton == submitButtonType) {
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

                        handleButtonBorrow(userName, document, result); // xử lí borrow
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

    public static void handleButtonBorrow(String userName, Documents document, BorrowRecord borrowRecord) {
        Request request = new Request(userName, document.getID(), "borrow", document.getTitle());
        request.setBorrowDate(borrowRecord.getBorrowDate());
        request.setReturnDate(borrowRecord.getReturnDate());
        RequestUtil requestUtil = new RequestUtil();
        boolean res = requestUtil.addRequest(request);

        if(res) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Request Sent");
            alert.setHeaderText(null);
            alert.setContentText("Your borrow request has been sent!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to send request. Please try again later.");
            alert.showAndWait();
        }
    }
}