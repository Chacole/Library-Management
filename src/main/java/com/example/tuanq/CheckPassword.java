package com.example.tuanq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckPassword {
    // Kiểm tra mật khẩu người dùng
    public static boolean checkPasswordOfUser(String email, String password) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "SELECT Password FROM accounts WHERE Email = ? LIMIT 1;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return password.equals(rs.getString("Password"));
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Kiểm tra mật khẩu người quản lý
    public static boolean checkPasswordOfAmin(String email, String password) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "SELECT Password FROM manageraccount WHERE Email = ? LIMIT 1;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return password.equals(rs.getString("Password"));
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkPasswordForEdit(int UserID, String password) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "SELECT Password FROM accounts WHERE AccountID = ? LIMIT 1;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, UserID);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return password.equals(rs.getString("Password"));
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}