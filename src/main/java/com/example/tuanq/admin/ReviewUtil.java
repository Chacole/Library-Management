package com.example.tuanq.admin;

import com.example.tuanq.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class ReviewUtil implements DAO<Review> {

    public static ReviewUtil getInstance() {
        return new ReviewUtil();
    }

    @Override
    public int insert(Review review) {
        int addedRows = 0;
        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "CALL AddReview(?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, review.DocumentID);
            preparedStatement.setInt(2, review.UserID);
            preparedStatement.setDouble(3, review.Rating);
            preparedStatement.setString(4, review.Comment);

            addedRows = preparedStatement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addedRows;
    }

    @Override
    public int update(Review review) {
        int editedRows = 0;
//        String[] words = DocID_AND_UserID.split(",");
//        int DocumentIDf = Integer.parseInt(words[0]);
//        int UserIDf = Integer.parseInt(words[1]);
//        try {
//            Connection connection = DatabaseConnection.getConnection();
//
//            String sql = "CALL EditReview(?, ?, ?, ?, ?, ?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, DocumentIDf);
//            preparedStatement.setInt(2, UserIDf);
//            preparedStatement.setInt(3, review.DocumentID);
//            preparedStatement.setInt(4, review.UserID);
//            preparedStatement.setDouble(5, review.Rating);
//            preparedStatement.setString(6, review.Comment);
//
//            editedRows = preparedStatement.executeUpdate();
//
//            connection.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return editedRows;
    }

    @Override
    public int delete(Review review) {
        int deletedRows = 0;
        try {
            Connection connection = DatabaseConnection.getConnection();

            // Sử dụng CallableStatement
            String sql = "{CALL DeleteReview(?, ?)}";
            CallableStatement callableStatement = connection.prepareCall(sql);

            // Gán tham số cho stored procedure
            callableStatement.setInt(1, review.DocumentID);
            callableStatement.setInt(2, review.UserID);

            // Thực thi stored procedure
            callableStatement.execute();

            // Lấy số hàng bị ảnh hưởng (ROW_COUNT)
            deletedRows = callableStatement.getUpdateCount();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deletedRows;
    }

    @Override
    public ArrayList<Review> select(Review review) {
        ArrayList<Review> reviews = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "CALL FindReview(?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, review.DocumentID);
            preparedStatement.setInt(2, review.UserID);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Review nReview = new Review(rs.getInt("DocumentID"),
                        rs.getInt("UserID"), rs.getString("UserName"),
                        rs.getDouble("Rating"), rs.getString("Comment"));
                reviews.add(nReview);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }
}