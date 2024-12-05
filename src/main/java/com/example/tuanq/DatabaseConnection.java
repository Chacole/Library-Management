package com.example.tuanq;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/Library"; // URL đến cơ sở dữ liệu
    private static final String DB_USER = "root"; // Tên người dùng MySQL
    private static final String DB_PASSWORD = "Tuan12345"; // Mật khẩu MySQL

    // Phương thức để lấy kết nối tới cơ sở dữ liệu
    public static Connection getConnection() {
        try {
            // Kết nối tới cơ sở dữ liệu
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Kết nối thất bại: " + e.getMessage());
        }
    }
}