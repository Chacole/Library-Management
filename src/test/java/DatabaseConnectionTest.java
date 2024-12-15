/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
import java.sql.Connection;
import org.junit.jupiter.api.Test;
import com.example.tuanq.DatabaseConnection;

import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Nguyễn Thanh Tùng
 */
public class DatabaseConnectionTest {
    
    public DatabaseConnectionTest() {
    }

    @Test
    public void testGetDatabaseConnection() {
    }

    @Test
    public void testGetConnection() {
        
        Connection connection = DatabaseConnection.getConnection();
        assertNotNull(connection);
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
