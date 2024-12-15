import com.example.tuanq.BookQR;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class BookQRTest {

    @Test
    void testGenerateQRCodeImage_validInput() {
        String text = "Test QR Code";
        try {
            BufferedImage qrCodeImage = BookQR.generateQRCodeImage(text);
            assertNotNull(qrCodeImage, "QR Code image should not be null");
            assertEquals(90, qrCodeImage.getWidth(), "QR Code width should be 90");
            assertEquals(120, qrCodeImage.getHeight(), "QR Code height should be 120");
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }


    @Test
    void testCreateQRCodeImageView_validInput() {
        String text = "Test QR Code";
        ImageView imageView = BookQR.createQRCodeImageView(text);
        assertNotNull(imageView, "ImageView should not be null");
        assertEquals(90, imageView.getFitWidth(), "ImageView width should be 90");
        assertEquals(120, imageView.getFitHeight(), "ImageView height should be 120");
    }


    @Test
    void testGenerateQRCodeImage_invalidInput() {
        String text = null;
        assertThrows(Exception.class, () -> {
            BookQR.generateQRCodeImage(text);
        }, "Expected an exception for null input");
    }
}