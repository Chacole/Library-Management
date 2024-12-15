package com.example.tuanq.admin;

import com.example.tuanq.Main;
import com.example.tuanq.customer.Profile;
import com.example.tuanq.designpattern.Command.Command;
import com.example.tuanq.designpattern.Command.ConcreteCommand;
import com.example.tuanq.designpattern.Command.Navigate;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class LibraryAdminController {
    private Stage primaryStage;

    @FXML
    private Button buttonSignOut;

    @FXML
    private Button buttonNotification;

    @FXML
    private VBox contentBox;

    @FXML
    private HBox ButtonBox;

    protected Navigate navigate = new Navigate(Main.getPrimaryStage());

    @FXML
    private void handleSignOut() {
        Profile.getInstance().clearSession();

        try {
            Stage stage = Main.getPrimaryStage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/login.fxml"));
            Scene loginScene = new Scene(loader.load());

            stage.setWidth(800);
            stage.setHeight(600);
            stage.centerOnScreen();

            // Tạo Command để chuyển đổi cảnh
            Command switchToLoginScene = new ConcreteCommand(new Navigate(stage), loginScene);

            // Thực thi Command
            switchToLoginScene.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExit() {
        Exit exit = new Exit();
        exit.exitSystem();
    }

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

        // Kiểm tra và hiển thị kết quả
        if (documents == null || documents.isEmpty()) {
            System.out.println("No documents found");
            contentBox.getChildren().clear(); // Xóa nội dung cũ
        } else {
            try {
                // Tải view hiển thị kết quả tìm kiếm
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/admin/researchDocuments.fxml"));
                Parent researchView = loader.load();

                // Lấy controller của view kết quả
                DisplaydocumentsResearch displayDocumentsResearch = loader.getController();
                displayDocumentsResearch.setDocuments(documents);

                contentBox.getChildren().clear(); // Xóa nội dung cũ
                contentBox.getChildren().add(researchView); // Hiển thị nội dung mới
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleDisplayDocument() {
        try {
            // Tải documents.fxml và thêm vào contentBox
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/admin/documents.fxml"));
            Parent documentView = loader.load();
            Displaydocuments displayDocumentsController = loader.getController();

            contentBox.getChildren().clear();
            contentBox.getChildren().add(documentView);

            // button research
            Button buttonResearch = new Button();
            buttonResearch.setMaxHeight(40);
            buttonResearch.setMaxWidth(40);
            buttonResearch.setMinHeight(40);
            buttonResearch.setMinWidth(40);
            buttonResearch.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
            ImageView researchIcon = new ImageView(getClass().getResource("/images/research.png").toExternalForm());
            researchIcon.setFitHeight(30);
            researchIcon.setFitWidth(30);
            buttonResearch.setGraphic(researchIcon);

            buttonResearch.setOnAction(event -> {
                // Tạo Dialog
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
                yearPublishedField.setPromptText("YearPublished");
                yearPublishedField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        yearPublishedField.setText(oldValue); // Chỉ cho phép số
                    }
                });

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

                // Lắng nghe sự thay đổi dữ liệu và thực hiện tìm kiếm ngay lập tức
                authorField.textProperty().addListener((observable, oldValue, newValue) -> performSearch(authorField, titleField, typeField, yearPublishedField));
                titleField.textProperty().addListener((observable, oldValue, newValue) -> performSearch(authorField, titleField, typeField, yearPublishedField));
                typeField.textProperty().addListener((observable, oldValue, newValue) -> performSearch(authorField, titleField, typeField, yearPublishedField));
                yearPublishedField.textProperty().addListener((observable, oldValue, newValue) -> performSearch(authorField, titleField, typeField, yearPublishedField));

                // Hiển thị Dialog
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                dialog.show();
            });

            // Tải buttonsdocument.fxml và xử lý các button từ file FXML
            FXMLLoader buttonLoader = new FXMLLoader(getClass().getResource("/com/example/tuanq/admin/buttonsdocument.fxml"));
            HBox buttonView = buttonLoader.load();
            toolsDocuments toolsController = buttonLoader.getController();

            toolsController.setDisplayDocumentsController(displayDocumentsController);

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

    private void performUserSearch(TextField nameField, TextField emailField, TextField addressField, TextField phoneField) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();

        // Tạo đối tượng chứa tiêu chí tìm kiếm
        Users searchCriteria = new Users(name, email, address, phone);

        // Gọi UserUtil để tìm kiếm
        UserUtil userUtil = new UserUtil();
        ArrayList<Users> users = userUtil.select(searchCriteria);

        // Hiển thị kết quả
        if (users == null || users.isEmpty()) {
            System.out.println("No users found");
            contentBox.getChildren().clear(); // Xóa nội dung cũ
        } else {
            try {
                // Tải view hiển thị kết quả tìm kiếm
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/admin/researchUsers.fxml"));
                Parent researchView = loader.load();

                // Lấy controller của view kết quả
                DisplayusersResearch displayUsersResearch = loader.getController();
                displayUsersResearch.setUsers(users);

                contentBox.getChildren().clear(); // Xóa nội dung cũ
                contentBox.getChildren().add(researchView); // Hiển thị nội dung mới
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        ImageView researchIcon = new ImageView(getClass().getResource("/images/research.png").toExternalForm());
        researchIcon.setFitHeight(30);
        researchIcon.setFitWidth(30);
        buttonResearch.setGraphic(researchIcon);


        buttonResearch.setOnAction(event -> {
            // Tạo Dialog cho tìm kiếm user
            Dialog<Users> dialog = new Dialog<>();
            dialog.setTitle("Research User Form");
            dialog.setHeaderText("Please enter your details below:");

            dialog.getDialogPane().setPrefSize(400, 500);

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

            // Lắng nghe sự thay đổi dữ liệu và thực hiện tìm kiếm ngay lập tức
            nameField.textProperty().addListener((observable, oldValue, newValue) -> performUserSearch(nameField, emailField, addressField, phoneField));
            emailField.textProperty().addListener((observable, oldValue, newValue) -> performUserSearch(nameField, emailField, addressField, phoneField));
            addressField.textProperty().addListener((observable, oldValue, newValue) -> performUserSearch(nameField, emailField, addressField, phoneField));
            phoneField.textProperty().addListener((observable, oldValue, newValue) -> performUserSearch(nameField, emailField, addressField, phoneField));

            // Hiển thị Dialog
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.show();
        });

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/admin/buttonsuser.fxml"));
            HBox buttonView = loader.load();
            toolsUsers toolsController = loader.getController();

            toolsController.setDisplayDocumentsController(displayUsers);

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
    private void handleRequesting() {
        try {
            Parent documentView = FXMLLoader.load(getClass().getResource("/com/example/tuanq/admin/requestsView.fxml"));
            contentBox.getChildren().clear();
            contentBox.getChildren().add(documentView);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDisplayBorrowRecord() {
        contentBox.getChildren().clear();
        DisplayBorrowRecord displayborrowrecord = new DisplayBorrowRecord();
        contentBox.getChildren().add(displayborrowrecord.createBorrowRecordTable());

        ButtonBox.getChildren().clear();
    }

    @FXML
    private void handleSearch() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tuanq/admin/Search.fxml"));
            Parent documentView = loader.load();

            contentBox.getChildren().clear();
            contentBox.getChildren().add(documentView);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}