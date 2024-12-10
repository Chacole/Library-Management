package com.example.tuanq;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ActivityUtil {
    public static int addActivity(int UserID, int DocumentID) {
        int addedRows = 0;
        String Request = "";
        String Status = "pending";
        if (true) { // sửa theo điều kiện là user ấn nút "yêu cầu mượn"
            Request = "borrow";
        }
        if (false) { // sửa theo điều kiện là user ấn nút "yêu cầu trả"
            Request = "return";
        }
        if (true) { // sửa theo điều kiện là admin ấn nút "confirm"
            Status = "confirmed";
        }
        if (false) { // sửa theo điều kiện là admin ấn nút "deny"
            Status = "denied";
        }
        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "INSERT INTO Activities(UserID, Request, DocumentID, Status, Time)\n" +
                    "VALUES (?, ?, ?, ?, NOW());";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, UserID);
            preparedStatement.setString(2, Request);
            preparedStatement.setInt(3, DocumentID);
            preparedStatement.setString(4, Status);


            addedRows = preparedStatement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addedRows;
    }
}
