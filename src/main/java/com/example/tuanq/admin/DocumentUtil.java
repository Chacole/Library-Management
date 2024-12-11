package com.example.tuanq.admin;

import com.example.tuanq.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class DocumentUtil implements DAO<Documents> {

    public static DocumentUtil getInstance() {
        return new DocumentUtil();
    }

    @Override
    public int insert(Documents document) {
        int addedRows = 0;
        try {
            Connection connection = DatabaseConnection.getConnection();

            // Tạo câu lệnh SQL từ các giá trị
            String sql = String.format(
                    "CALL AddDocument('%s', '%s', '%s', %d, %d, '%s')",
                    escapeSQL(document.Author),
                    escapeSQL(document.Title),
                    escapeSQL(document.Type),
                    document.YearPublished,
                    document.Quantity,
                    escapeSQL(document.imagePath)
            );

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                addedRows = rs.getInt("RowsAffected");
            }

            System.out.println("Add executed. Rows affected: " + addedRows);

            connection.close();
        } catch (SQLException e) {
            System.err.println("SQL error during add: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addedRows;
    }

    @Override
    public int update(Documents document) {
        int editedRows = 0;
        try {
            Connection connection = DatabaseConnection.getConnection();

            // Escape các giá trị đầu vào
            String author = escapeSQL(document.Author);
            String title = escapeSQL(document.Title);
            String type = escapeSQL(document.Type);
            String imagePath = escapeSQL(document.imagePath);

            // Xây dựng câu lệnh SQL
            String sql = String.format(
                    "CALL EditDocument(%d, '%s', '%s', '%s', %d, %d, '%s')",
                    document.getID(), author, title, type,
                    document.YearPublished, document.Quantity, imagePath
            );

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            // Lấy số dòng bị ảnh hưởng từ ROW_COUNT()
            if (rs.next()) {
                editedRows = rs.getInt("RowsAffected");
            }

            System.out.println("Update executed. Rows affected: " + editedRows);

            rs.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("SQL error during update: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return editedRows;
    }

    @Override
    public int delete(Documents document) {
        int deletedRows = 0;
        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = String.format(
                    "CALL DeleteDocument('%s', '%s', %d)",
                    escapeSQL(document.Title),
                    escapeSQL(document.Author),
                    document.YearPublished
            );

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                deletedRows = rs.getInt("RowsAffected");
            }

            System.out.println("Delete executed. Rows affected: " + deletedRows);

            rs.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("SQL error during delete: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deletedRows;
    }

    @Override
    public ArrayList<Documents> select(Documents document) {
        ArrayList<Documents> documents = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "CALL FindDocument(?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, document.Author);
            preparedStatement.setString(2, document.Title);
            preparedStatement.setString(3, document.Type);
            preparedStatement.setInt(4, document.YearPublished);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Documents nDocument = new Documents(rs.getString("Author"),
                        rs.getString("Title"), rs.getString("Type"),
                        rs.getInt("Year"), rs.getInt("Quantity"),
                        rs.getString("imagePath"));

                nDocument.setID(rs.getInt("ID"));

                documents.add(nDocument);
            }

            rs.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documents;
    }

    private String escapeSQL(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("'", "''"); // Thay dấu nháy đơn thành hai dấu nháy đơn để thoát
    }
}
