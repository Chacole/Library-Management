package com.example.tuanq;

import java.sql.*;
import java.util.Date;
import java.sql.Connection;

public class RecordUtil {
    public boolean addBorrowRecord(BorrowRecord borrow) {
        boolean isInserted = false;

        try {
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Database connection established.");

            // Kiểm tra userName trong bảng Users
            String checkUserQuery = "SELECT ID FROM Users WHERE Name = ?";
            PreparedStatement checkUserStmt = connection.prepareStatement(checkUserQuery);
            checkUserStmt.setString(1, borrow.getUserName());
            ResultSet userResult = checkUserStmt.executeQuery();

            int userID = -1;
            if (userResult.next()) {
                userID = userResult.getInt("ID");
                System.out.println("User found with ID: " + userID);
            } else {
                System.out.println("User không tồn tại.");
                return false; // Dừng nếu user không tồn tại
            }

            // Kiểm tra documentTitle trong bảng Documents
            String checkDocumentQuery = "SELECT ID FROM Documents WHERE Title = ?";
            PreparedStatement checkDocumentStmt = connection.prepareStatement(checkDocumentQuery);
            checkDocumentStmt.setString(1, borrow.getDocumentTitle());
            ResultSet documentResult = checkDocumentStmt.executeQuery();

            int documentID = -1;
            if (documentResult.next()) {
                documentID = documentResult.getInt("ID");
                System.out.println("Document found with ID: " + documentID);
            } else {
                System.out.println("Document không tồn tại.");
                return false; // Dừng nếu document không tồn tại
            }

            try {
                // Gọi Stored Procedure để thêm BorrowRecord
                String sql = "{CALL AddRecord(?, ?, ?, ?, ?)}";
                CallableStatement callableStmt = connection.prepareCall(sql);
                callableStmt.setInt(1, userID);
                callableStmt.setInt(2, documentID);
                callableStmt.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
                callableStmt.setDate(4, new java.sql.Date(borrow.getReturnDate().getTime()));
                callableStmt.registerOutParameter(5, java.sql.Types.INTEGER);

                callableStmt.execute();

                int rowsAffected = callableStmt.getInt(5);

                isInserted = rowsAffected > 0;

                callableStmt.close();
                connection.close();
            } catch (SQLException e) {
                if (e.getSQLState().equals("45000")) {
                    System.out.println("Lỗi: " + e.getMessage()); // Thông báo lỗi từ Trigger
                } else {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }

        return isInserted;
    }

    public int deleteBorrowRecord(BorrowRecord borrow) {
        int deletedRows = 0;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String checkUserQuery = "SELECT ID FROM Users WHERE Name = ?";
            PreparedStatement checkUserStmt = connection.prepareStatement(checkUserQuery);
            checkUserStmt.setString(1, borrow.getUserName());
            ResultSet userResult = checkUserStmt.executeQuery();

            int userID = -1;
            if (userResult.next()) { // Di chuyển con trỏ đến dòng đầu tiên
                userID = userResult.getInt("ID");
                System.out.println("UserID found: " + userID);
            }

            String checkDocumentQuery = "SELECT ID FROM Documents WHERE Title = ?";
            PreparedStatement checkDocumentStmt = connection.prepareStatement(checkDocumentQuery);
            checkDocumentStmt.setString(1, borrow.getDocumentTitle());
            ResultSet documentResult = checkDocumentStmt.executeQuery();

            int documentID = -1;
            if (documentResult.next()) { // Di chuyển con trỏ đến dòng đầu tiên
                documentID = documentResult.getInt("ID");
                System.out.println("DocumentID found: " + documentID);
            }

            // Gọi Stored Procedure để xóa bản ghi BorrowRecord
            String sql = "CALL DeleteRecord(?, ?)";
            CallableStatement callableStmt = connection.prepareCall(sql);
            callableStmt.setInt(1, userID);
            callableStmt.setInt(2, documentID);

            deletedRows = callableStmt.executeUpdate();

            callableStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deletedRows;
    }
}
