import com.example.tuanq.ApiService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiServiceTest {

    @Test
    void testGetGoogleBookImageWithInvalidQuery() {
        // Truy vấn không hợp lệ (không có sách nào phù hợp)
        String query = "nonexistentbookquery123456";

        // Gọi phương thức API
        String imageUrl = ApiService.getGoogleBookImage(query);

        // Kiểm tra kết quả null (không tìm thấy sách)
        assertNull(imageUrl, "Image URL should be null for an invalid query.");
    }

    @Test
    void testGetGoogleBookImageWithEmptyQuery() {
        // Truy vấn rỗng
        String query = "";

        // Gọi phương thức API
        String imageUrl = ApiService.getGoogleBookImage(query);

        // Kiểm tra kết quả null (truy vấn rỗng không trả về kết quả)
        assertNull(imageUrl, "Image URL should be null for an empty query.");
    }


    @Test
    void testGetGoogleBookImageWithLargeQuery() {
        // Truy vấn dài
        String query = "abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890";

        // Gọi phương thức API
        String imageUrl = ApiService.getGoogleBookImage(query);

        // Kiểm tra kết quả không null (nếu API xử lý truy vấn dài)
        assertNotNull(imageUrl, "Image URL should not be null for a large query.");

        // Kiểm tra kết quả phải là URL hợp lệ
        assertTrue(imageUrl.startsWith("http"), "Image URL should start with 'http'.");
    }
}
