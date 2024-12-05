package com.example.tuanq;
import com.example.tuanq.handleMainButton.Exit;
import com.example.tuanq.handleMainButton.Displayusers;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class LibraryController {
    private Stage primaryStage;

    @FXML
    private VBox contentBox;

    @FXML
    private HBox ButtonBox;
    // Các phương thức xử lý sự kiện
    @FXML
    private void handleExit() {
        Exit exit = new Exit();
        exit.exitSystem();
    }

    @FXML
    private void handleDisplayDocument() {
        try {
            // Tải documents.fxml và thêm vào contentBox
            Parent documentView = FXMLLoader.load(getClass().getResource("/com/example/tuanq/documents.fxml"));
            contentBox.getChildren().clear();
            contentBox.getChildren().add(documentView);

            // button research
            Button buttonResearch = new Button();
            buttonResearch.setMaxHeight(40);
            buttonResearch.setMaxWidth(40);
            buttonResearch.setMinHeight(40);
            buttonResearch.setMinWidth(40);
            buttonResearch.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
            ImageView researchIcon = new ImageView(getClass().getResource("/com/example/tuanq/images/research.png").toExternalForm());
            researchIcon.setFitHeight(30);
            researchIcon.setFitWidth(30);
            buttonResearch.setGraphic(researchIcon);

            buttonResearch.setOnAction(event -> {
                // Tạo Dialog
                Dialog<Documents> dialog = new Dialog<>();
                dialog.setTitle("Research Document Form");
                dialog.setHeaderText("Please enter your details below:");

                // Thêm các nút OK và Cancel
                ButtonType okButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

                Button submitButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
                submitButton.setDisable(true);

                dialog.getDialogPane().setPrefSize(400, 500);

                // Tạo các trường nhập liệu
                TextField authorField = new TextField();
                authorField.setPromptText("Author");

                TextField titleField = new TextField();
                titleField.setPromptText("Title");

                TextField typeField = new TextField();
                typeField.setPromptText("Type");

                TextField yearPublishedField = new TextField();
                yearPublishedField.setPromptText("YearPublished");
                yearPublishedField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        yearPublishedField.setText(oldValue); // Chỉ cho phép số
                    }
                });

                submitButton.disableProperty().bind(
                        Bindings.createBooleanBinding(() ->
                                        authorField.getText().trim().isEmpty() &&
                                                titleField.getText().trim().isEmpty() &&
                                                typeField.getText().trim().isEmpty() &&
                                                yearPublishedField.getText().trim().isEmpty(),
                                authorField.textProperty(),
                                titleField.textProperty(),
                                typeField.textProperty(),
                                yearPublishedField.textProperty()
                        )
                );

                // Sắp xếp giao diện các trường nhập trong GridPane
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

                // Xử lý kết quả
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == okButtonType) {
                        String author = authorField.getText().trim();
                        String title = titleField.getText().trim();
                        String type = typeField.getText().trim();
                        int year = yearPublishedField.getText().trim().isEmpty() ? -1 : Integer.parseInt(yearPublishedField.getText().trim());
                        return new Documents(author, title, type, year, 0);
                    }
                    return null;
                });

                // Hiển thị Dialog và nhận kết quả
                Optional<Documents> result = dialog.showAndWait();
                result.ifPresent(userData -> {
                    System.out.println("Author: " + userData.getAuthor());
                    System.out.println("Title: " + userData.getTitle());
                    System.out.println("Type: " + userData.getType());
                    System.out.println("YearPublished: " + userData.getYear());

                    DocumentUtil documentutil = new DocumentUtil();
                    ArrayList<Documents> documents = documentutil.select(userData);

                    if (documents == null || documents.isEmpty()) {
                        System.out.println("No documents found");
                    } else {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/researchDocuments.fxml"));
                            Parent researchView = loader.load();

                            // Lấy controller của DisplaydocumentsResearch
                            DisplaydocumentsResearch displayDocumentsResearch = loader.getController();
                            displayDocumentsResearch.setDocuments(documents); // Truyền dữ liệu vào ListView

                            contentBox.getChildren().clear(); // Làm sạch nội dung cũ
                            contentBox.getChildren().add(researchView); // Thêm nội dung mới
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            });

            // Tải buttonsdocument.fxml và xử lý các button từ file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/buttonsdocument.fxml"));
            HBox buttonView = loader.load(); // Chỉ tải phần `HBox` từ FXML

            Region spacer = new Region();
            ButtonBox.setHgrow(spacer, Priority.ALWAYS);

            // Làm sạch ButtonBox và thêm buttonResearch + các button khác
            ButtonBox.getChildren().clear();
            ButtonBox.getChildren().add(buttonResearch); // Thêm buttonResearch
            ButtonBox.getChildren().add(spacer);
            ButtonBox.getChildren().addAll(buttonView.getChildren());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDisplayUser() {
        contentBox.getChildren().clear();
        Displayusers displayUsers = new Displayusers();
        contentBox.getChildren().add(displayUsers.createUserTable());

        // button research
        Button buttonResearch = new Button();
        buttonResearch.setMaxHeight(40);
        buttonResearch.setMaxWidth(40);
        buttonResearch.setMinHeight(40);
        buttonResearch.setMinWidth(40);
        buttonResearch.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        ImageView researchIcon = new ImageView(getClass().getResource("/com/example/tuanq/images/research.png").toExternalForm());
        researchIcon.setFitHeight(30);
        researchIcon.setFitWidth(30);
        buttonResearch.setGraphic(researchIcon);


        buttonResearch.setOnAction(event -> {
            // Tạo Dialog cho tìm kiếm user
            Dialog<Users> dialog = new Dialog<>();
            dialog.setTitle("Research User Form");
            dialog.setHeaderText("Please enter your details below:");

            // Thêm các nút OK và Cancel
            ButtonType okButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

            Button submitButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
            submitButton.setDisable(true);

            dialog.getDialogPane().setPrefSize(400, 300);

            // Tạo các trường nhập liệu
            TextField nameField = new TextField();
            nameField.setPromptText("Name");

            TextField emailField = new TextField();
            emailField.setPromptText("Email");

            TextField addressField = new TextField();
            addressField.setPromptText("Address");

            TextField phoneField = new TextField();
            phoneField.setPromptText("Phone");

            submitButton.disableProperty().bind(
                    Bindings.createBooleanBinding(() ->
                                    nameField.getText().trim().isEmpty() &&
                                            emailField.getText().trim().isEmpty() &&
                                            addressField.getText().trim().isEmpty() &&
                                            phoneField.getText().trim().isEmpty(),
                            nameField.textProperty(),
                            emailField.textProperty(),
                            addressField.textProperty(),
                            phoneField.textProperty()
                    )
            );

            // Sắp xếp giao diện các trường nhập trong GridPane
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(20);

            grid.add(new Label("Name:"), 0, 0);
            grid.add(nameField, 1, 0);

            grid.add(new Label("Email:"), 0, 1);
            grid.add(emailField, 1, 1);

            grid.add(new Label("Address:"), 0, 2);
            grid.add(addressField, 1, 2);

            grid.add(new Label("Phone:"), 0, 3);
            grid.add(phoneField, 1, 3);

            GridPane.setHgrow(nameField, Priority.ALWAYS);
            GridPane.setHgrow(emailField, Priority.ALWAYS);
            GridPane.setHgrow(addressField, Priority.ALWAYS);
            GridPane.setHgrow(phoneField, Priority.ALWAYS);

            dialog.getDialogPane().setContent(grid);

            // Xử lý kết quả
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == okButtonType) {
                    String name = nameField.getText().trim();
                    String email = emailField.getText().trim();
                    String address = addressField.getText().trim();
                    String phone = phoneField.getText().trim();
                    return new Users(name, email, address, phone);
                }
                return null;
            });

            // Hiển thị Dialog và nhận kết quả
            Optional<Users> result = dialog.showAndWait();
            result.ifPresent(userData -> {
                System.out.println("Name: " + userData.getName());
                System.out.println("Email: " + userData.getEmail());
                System.out.println("Address: " + userData.getAddress());
                System.out.println("Phone: " + userData.getPhone());

                UserUtil userUtil = new UserUtil();
                ArrayList<Users> users = userUtil.select(userData);

                if (users == null || users.isEmpty()) {
                    System.out.println("No users found");
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/researchUsers.fxml"));
                        Parent researchView = loader.load();

                        // Lấy controller của DisplayusersResearch
                        DisplayusersResearch displayUsersResearch = loader.getController();
                        displayUsersResearch.setUsers(users); // Truyền dữ liệu vào TableView

                        contentBox.getChildren().clear(); // Làm sạch nội dung cũ
                        contentBox.getChildren().add(researchView); // Thêm nội dung mới
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/buttonsuser.fxml"));
            HBox buttonView = loader.load();

            Region spacer = new Region();
            ButtonBox.setHgrow(spacer, Priority.ALWAYS);

            ButtonBox.getChildren().clear();
            ButtonBox.getChildren().add(buttonResearch); // Thêm buttonResearch
            ButtonBox.getChildren().add(spacer);
            ButtonBox.getChildren().addAll(buttonView.getChildren());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBorrowDocument() {
        contentBox.getChildren().clear();
        DisplayBorrowRecord displayborrowrecord = new DisplayBorrowRecord();
        contentBox.getChildren().add(displayborrowrecord.createBorrowRecordTable());

        ButtonBox.getChildren().clear();
    }
}