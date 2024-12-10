package com.example.tuanq;

import java.sql.Connection;;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckEmail {

    // Kiểm tra email đã tồn tại trong database chưa
    public static boolean isEmailExist(String email) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String query = "SELECT COUNT(*) FROM users WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email.toLowerCase());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Nếu COUNT > 0, email đã tồn tại
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
