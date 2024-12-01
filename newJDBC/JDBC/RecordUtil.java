package JDBC;

import Model.BorrowRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RecordUtil implements DAO<BorrowRecord> {

    public static RecordUtil getInstance() {
        return new RecordUtil();
    }

    @Override
    public int insert(BorrowRecord borrowRecord) {
        int addedRows = 0;
        try {
            Connection connection = ConnectDB.getConnection();

            String sql = "CALL AddRecord(?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, borrowRecord.UserID);
            preparedStatement.setInt(2, borrowRecord.DocumentID);
            if (borrowRecord.BorrowDate != null && !borrowRecord.BorrowDate.trim().isEmpty()) {
                preparedStatement.setString(3, borrowRecord.BorrowDate);
            } else {
                preparedStatement.setNull(3, java.sql.Types.DATE);
            }

            addedRows = preparedStatement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addedRows;
    }

    @Override
    public int update(BorrowRecord borrowRecord, String borrowID) {
        int editedRows = 0;
        try {
            Connection connection = ConnectDB.getConnection();

            String sql = "CALL EditRecord(?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(borrowID));
            preparedStatement.setInt(2, borrowRecord.UserID);
            preparedStatement.setInt(3, borrowRecord.DocumentID);
            if (borrowRecord.BorrowDate != null && !borrowRecord.BorrowDate.trim().isEmpty()) {
                preparedStatement.setString(4, borrowRecord.BorrowDate);
            } else {
                preparedStatement.setNull(4, java.sql.Types.DATE);
            }
            if (borrowRecord.ReturnDate != null && !borrowRecord.ReturnDate.trim().isEmpty()) {
                preparedStatement.setString(5, borrowRecord.ReturnDate);
            } else {
                preparedStatement.setNull(5, java.sql.Types.DATE);
            }

            editedRows = preparedStatement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return editedRows;
    }

    @Override
    public int delete(BorrowRecord borrowRecord) {
        int deletedRows = 0;
        try {
            Connection connection = ConnectDB.getConnection();

            String sql = "CALL DeleteRecord(?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, borrowRecord.UserID);
            preparedStatement.setInt(2, borrowRecord.DocumentID);

            deletedRows = preparedStatement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deletedRows;
    }

    @Override
    public ArrayList<BorrowRecord> select(BorrowRecord borrowRecord) {
        ArrayList<BorrowRecord> borrowRecords = new ArrayList<>();
        try {
            Connection connection = ConnectDB.getConnection();

            String sql = "CALL FindRecord(?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, borrowRecord.BorrowID);
            preparedStatement.setString(2, borrowRecord.UserName);
            preparedStatement.setString(3, borrowRecord.DocumentTitle);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                BorrowRecord nRecord = new BorrowRecord(rs.getInt("BorrowID"),
                        rs.getInt("UserID"), rs.getString("UserName"),
                        rs.getInt("DocumentID"), rs.getString("DocumentTitle"),
                        rs.getString("BorrowDate"), rs.getString("ReturnDate"));
                borrowRecords.add(nRecord);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return borrowRecords;
    }
}
