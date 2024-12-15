package com.example.tuanq.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.ArrayList;

public class HandleRequest {

    @FXML
    private TableView<Request> requestTable;

    @FXML
    private TableColumn<Request, String> userNameColumn;

    @FXML
    private TableColumn<Request, String> documentTitleColumn;

    @FXML
    private TableColumn<Request, String> statusColumn;

    @FXML
    private TableColumn<Request, Void> actionColumn;

    @FXML
    public void initialize() {
        RequestUtil requestUtil = new RequestUtil();
        ArrayList<Request> requestList = requestUtil.getPendingRequests();

        ObservableList<Request> observableRequests = FXCollections.observableArrayList(requestList);
        requestTable.setItems(observableRequests);

        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        documentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        addActionButtons();
    }

    private void addActionButtons() {
        Callback<TableColumn<Request, Void>, TableCell<Request, Void>> cellFactory = param -> new TableCell<>() {
            private final Button confirmButton = new Button("Confirm");

            {
                confirmButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-padding: 5;");
                confirmButton.setOnAction(event -> {
                    Request request = getTableView().getItems().get(getIndex());

                    RequestUtil requestUtil = new RequestUtil();
                    confirmButton.setText("Submitted");
                    confirmButton.setDisable(true);

                    boolean isUpdated = requestUtil.updateRequestStatus(request.getDocumentId(), "borrowed");
                    if (isUpdated) {
                        request.setStatus("borrowed");
                        getTableView().refresh();
                    }

                    BorrowRecord borrowRecord = new BorrowRecord(request.getUserName(), request.getTitle(), request.getBorrowDate(), request.getReturnDate());
                    boolean r = new RecordUtil().addBorrowRecord(borrowRecord);
                    if (r) {
                        System.out.println("Borrow record đã được thêm thành công.");
                    } else {
                        System.out.println("Thêm borrow record thất bại.");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(confirmButton);
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }
}