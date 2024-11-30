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

            String sql = "CALL AddDocument(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, document.Title);
            preparedStatement.setString(2, document.ISBN);
            preparedStatement.setString(3, document.Type);
            preparedStatement.setString(4, document.Author);
            preparedStatement.setString(5, document.Publisher);
            preparedStatement.setInt(6, document.YearPublished);
            preparedStatement.setInt(7, document.Quantity);

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

            String sql = "CALL EditDocument(?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(documentID));
            preparedStatement.setString(2, document.Title);
            preparedStatement.setString(3, document.ISBN);
            preparedStatement.setString(4, document.Type);
            preparedStatement.setString(5, document.Author);
            preparedStatement.setString(6, document.Publisher);
            preparedStatement.setInt(7, document.YearPublished);
            preparedStatement.setInt(8, document.Quantity);

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
            preparedStatement.setInt(3, document.YearPublished);

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
            preparedStatement.setString(1, document.ISBN);
            preparedStatement.setString(2, document.Title);
            preparedStatement.setString(3, document.Type);
            preparedStatement.setString(4, document.Author);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Document nDocument = new Document(rs.getInt("ID"), rs.getString("Title"),
                        rs.getString("ISBN"), rs.getString("Type"),
                        rs.getString("Author"), rs.getString("Publisher"),
                        rs.getInt("YearPublished"), rs.getInt("Quantity"));
                documents.add(nDocument);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documents;
    }
}
