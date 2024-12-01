package JDBC;

import Model.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DocumentUtil implements DAO<Document>{

    public static DocumentUtil getInstance() {
        return new DocumentUtil();
    }

    @Override
    public int insert(Document document) {
        int addedRows = 0;
        try {
            Connection connection = ConnectDB.getConnection();

            String sql = "CALL AddDocument(?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, document.Author);
            preparedStatement.setString(2, document.Title);
            preparedStatement.setString(3, document.Type);
            preparedStatement.setInt(4, document.Year);
            preparedStatement.setInt(5, document.Quantity);
            preparedStatement.setString(6, document.ImagePath);

            addedRows = preparedStatement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addedRows;
    }

    @Override
    public int update(Document document, String documentID) {
        int editedRows = 0;
        try {
            Connection connection = ConnectDB.getConnection();

            String sql = "CALL EditDocument(?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(documentID));
            preparedStatement.setString(2, document.Author);
            preparedStatement.setString(3, document.Title);
            preparedStatement.setString(4, document.Type);
            preparedStatement.setInt(5, document.Year);
            preparedStatement.setInt(6, document.Quantity);
            preparedStatement.setString(7, document.ImagePath);

            editedRows = preparedStatement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return editedRows;
    }

    @Override
    public int delete(Document document) {
        int deletedRows = 0;
        try {
            Connection connection = ConnectDB.getConnection();

            String sql = "CALL DeleteDocument(?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, document.Title);
            preparedStatement.setString(2, document.Author);
            preparedStatement.setInt(3, document.Year);

            deletedRows = preparedStatement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deletedRows;
    }

    @Override
    public ArrayList<Document> select(Document document) {
        ArrayList<Document> documents = new ArrayList<>();
        try {
            Connection connection = ConnectDB.getConnection();

            String sql = "CALL FindDocument(?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, document.Title);
            preparedStatement.setString(2, document.Type);
            preparedStatement.setString(3, document.Author);
            preparedStatement.setInt(4, document.Year);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Document nDocument = new Document(rs.getInt("ID"), rs.getString("Author"),
                        rs.getString("Title"), rs.getString("Type"),
                        rs.getInt("Year"), rs.getInt("Quantity"),
                        rs.getString("ImagePath"));
                documents.add(nDocument);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documents;
    }
}
