package com.example.tuanq;

import com.example.tuanq.admin.Documents;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DocumentDataLoader {
    private ObservableList<Documents> cachedDocuments;

    // Tạo một Task để tải dữ liệu từ cơ sở dữ liệu
    public Task<ObservableList<Documents>> createLoadTask() {
        return new Task<>() {
            @Override
            protected ObservableList<Documents> call() throws Exception {
                if (cachedDocuments != null) {
                    return cachedDocuments;
                }

                ObservableList<Documents> documents = FXCollections.observableArrayList();
                String query = "SELECT * FROM Documents";

                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement statement = connection.prepareStatement(query);
                     ResultSet resultSet = statement.executeQuery()) {

                    int totalRecords = 0; // Đếm tổng số bản ghi
                    while (resultSet.next()) {
                        if (isCancelled()) {
                            break;
                        }

                        Documents document = new Documents(
                                resultSet.getInt("ID"),
                                resultSet.getString("Author"),
                                resultSet.getString("Title"),
                                resultSet.getString("Type"),
                                resultSet.getInt("Year"),
                                resultSet.getInt("Quantity"),
                                resultSet.getString("ImagePath"),
                                resultSet.getDouble("rating")
                        );
                        document.updateImageFromApi(); // Cập nhật hình ảnh

                        documents.add(document);
                        totalRecords++;

                        updateProgress(totalRecords, 70);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return documents;
            }
        };
    }
}