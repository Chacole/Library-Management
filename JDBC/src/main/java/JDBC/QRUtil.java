package JDBC;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class QRUtil {
    public static String selectImagePath(int documentID) {
        String imagepath = "";
        try {
            Connection connection = ConnectDB.getConnection();

            String sql = "SELECT ImagePath FROM Documents WHERE ID = ? LIMIT 1;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, documentID);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                imagepath = rs.getString("ImagePath");
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagepath;
    }

    /**
     * Tạo mã QR.
     *
     * @param imagePath link URL đến ảnh sách.
     * @param width độ rộng mã.
     * @param height độ cao mã.
     * @return ảnh mã QR.
     * @throws WriterException lỗi mã hóa.
     * @throws IOException lỗi Input/Output.
     */
    public static Image generateQRCodeImage(String imagePath, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(imagePath, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        return new Image(inputStream);
    }
}
