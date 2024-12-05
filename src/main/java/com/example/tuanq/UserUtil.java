package com.example.tuanq;

import java.sql.*;
import java.util.ArrayList;

public class UserUtil implements DAO<Users> {

    public static UserUtil getInstance() {
        return new UserUtil();
    }

    @Override
    public int insert(Users user) {
        int addedRows = 0;
        try {
            Connection connection = DatabaseConnection.getConnection();

            // Tạo câu lệnh SQL từ các giá trị
            String sql = String.format(
                    "CALL addUser('%s', '%s', '%s', '%s')",
                    escapeSQL(user.Name),
                    escapeSQL(user.Email),
                    escapeSQL(user.Address),
                    escapeSQL(user.Phone)
            );

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                addedRows = rs.getInt("RowsAffected");
            }

            System.out.println("User added. Rows affected: " + addedRows);

            connection.close();
        } catch (SQLException e) {
            System.err.println("SQL error during user insertion: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addedRows;
    }

    @Override
    public int update(Users user) {
        int editedRows = 0;
        try {
            Connection connection = DatabaseConnection.getConnection();

            // Escape các giá trị đầu vào
            String name = escapeSQL(user.getName());
            String email = escapeSQL(user.getEmail());
            String address = escapeSQL(user.getAddress());
            String phone = escapeSQL(user.getPhone());

            // Xây dựng câu lệnh SQL
            String sql = String.format(
                    "CALL EditUser(%d, '%s', '%s', '%s', '%s')",
                    user.getID(), name, email, address, phone
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
    public int delete(Users user) {
        int deletedRows = 0;

        try {
            Connection connection = DatabaseConnection.getConnection();

            // Tạo câu lệnh SQL từ các giá trị
            String sql = String.format(
                    "CALL DeleteUser('%s', '%s')",
                    escapeSQL(user.Name),
                    escapeSQL(user.Email)
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
    public ArrayList<Users> select(Users user) {
        ArrayList<Users> users = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();

            String sql = "CALL FindUser(?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setString(4, user.getPhone());

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Users foundUser = new Users(rs.getString("Name"),
                        rs.getString("Email"), rs.getString("Address"),
                        rs.getString("Phone"));

                foundUser.setID(rs.getInt("ID"));
                users.add(foundUser);
            }

            rs.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    private String escapeSQL(String value) {
        return value.replace("'", "''");
    }
}