package JDBC;

import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserUtil implements DAO<User> {

    public static UserUtil getInstance() {
        return new UserUtil();
    }

    @Override
    public int insert(User user) {
        int addedRows = 0;
        try {
            Connection connection = ConnectDB.getConnection();

            String sql = "CALL Register(?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.FullName);
            if (user.BirthDate != null && !user.BirthDate.trim().isEmpty()) {
                preparedStatement.setString(2, user.BirthDate);
            } else {
                preparedStatement.setNull(2, java.sql.Types.DATE);
            }
            preparedStatement.setString(3, user.Email);
            preparedStatement.setString(4, user.Phone);
            preparedStatement.setString(5, user.Address);
            preparedStatement.setString(6, user.Password);

            addedRows = preparedStatement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addedRows;
    }

    // need fix
    @Override
    public int update(User user,String emailFind) {
        int editedRows = 0;
        try {
            Connection connection = ConnectDB.getConnection();

            String sql = "CALL EditUser(?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, emailFind);
            preparedStatement.setString(2, user.FullName);
            if (user.BirthDate != null && !user.BirthDate.trim().isEmpty()) {
                preparedStatement.setString(3, user.BirthDate);
            } else {
                preparedStatement.setNull(3, java.sql.Types.DATE);
            }
            preparedStatement.setString(4, user.Email);
            preparedStatement.setString(5, user.Phone);
            preparedStatement.setString(6, user.Address);
            preparedStatement.setString(7, user.Password);

            editedRows = preparedStatement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return editedRows;
    }

    @Override
    public int delete(User user) {
        int deletedRows = 0;
        try {
            Connection connection = ConnectDB.getConnection();

            String sql = "CALL DeleteUser(?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.Email);

            deletedRows = preparedStatement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deletedRows;
    }

    @Override
    public ArrayList<User> select(User user) {
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection connection = ConnectDB.getConnection();

            String sql = "CALL FindUser(?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.FullName);
            preparedStatement.setString(2, user.Email);
            preparedStatement.setString(3, user.Phone);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User nUser = new User(rs.getInt("ID"), rs.getString("FullName"),
                        rs.getString("BirthDate"), rs.getString("Email"),
                        rs.getString("Phone"), rs.getString("Address"), "");
                users.add(nUser);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public static boolean checkPassword(String password, String email) {
        try {
            Connection connection = ConnectDB.getConnection();

            String sql = "SELECT Password FROM accounts WHERE Email = ? LIMIT 1;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();

            connection.close();
            return (password.equals(rs.getString("Password")));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
