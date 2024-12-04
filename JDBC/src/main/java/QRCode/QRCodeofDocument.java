package QRCode;

import JDBC.QRUtil;

import com.google.zxing.WriterException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class QRCodeofDocument extends Application {
    private static int documentID;

    @Override
    public void start(Stage primaryStage) {
        String imagePath = QRUtil.selectImagePath(documentID);
        if (imagePath == null || imagePath.isEmpty()) {
            System.err.println("Không tìm thấy imagePath từ database với ID: " + documentID);
            return;
        }
        int width = 300;
        int height = 300;

        try {
            // Tạo mã QR và chuyển thành Image
            Image qrImage = QRUtil.generateQRCodeImage(imagePath, width, height);

            // Hiển thị mã QR trong JavaFX
            ImageView imageView = new ImageView(qrImage);
            StackPane root = new StackPane(imageView);
            Scene scene = new Scene(root, width + 50, height + 50);

            primaryStage.setTitle("QRCode");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (WriterException | IOException e) {
            System.err.println("Lỗi khi tạo mã QR: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Chạy JavaFX
        launch(args);
    }
}
