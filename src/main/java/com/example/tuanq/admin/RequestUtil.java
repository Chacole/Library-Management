package com.example.tuanq.admin;
import com.example.tuanq.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RequestUtil {
    public boolean addRequest(Request request) {
        String sql = "INSERT INTO Request (userName, documentId, documentTitle, type, borrowDate, returnDate) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, request.getUserName());
            stmt.setInt(2, request.getDocumentId());
            stmt.setString(3, request.getTitle());
            stmt.setString(4, request.getType());

            java.sql.Date sqlBorrowDate = new java.sql.Date(request.getBorrowDate().getTime());
            java.sql.Date sqlReturnDate = new java.sql.Date(request.getReturnDate().getTime());

            stmt.setDate(5, sqlBorrowDate);
            stmt.setDate(6, sqlReturnDate);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Request> getPendingRequests() {
        ArrayList<Request> requests = new ArrayList<>();
        String sql = "SELECT * FROM Request WHERE status = 'pending'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Request request = new Request(
                        rs.getInt("id"),
                        rs.getString("userName"),
                        rs.getInt("documentID"),
                        rs.getString("documentTitle"),
                        rs.getString("status"),
                        rs.getString("type")
                );

                request.setBorrowDate(rs.getDate("borrowDate"));
                request.setReturnDate(rs.getDate("returnDate"));

                requests.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    public boolean updateRequestStatus(int documentId, String status) {
        String sql = "UPDATE Request SET status = ? WHERE documentId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, documentId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
