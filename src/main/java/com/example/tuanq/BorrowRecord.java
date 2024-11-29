package com.example.tuanq;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BorrowRecord {
    private String userName;
    private String documentTitle;
    private Date borrowDate;
    private Date returnDate;
    private String image;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    // Constructor mặc định
    public BorrowRecord() {}

    public BorrowRecord(String userName, String documentTitle, Date borrowDate, Date returnDate) {
        this.userName = userName;
        this.documentTitle = documentTitle;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    // Constructor đầy đủ
    public BorrowRecord(String userName, String documentTitle, Date borrowDate, Date returnDate, String image) {
        this.userName = userName;
        this.documentTitle = documentTitle;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.image = image;
    }

    /**
     * Getter và Setter.
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFormattedBorrowDate() {
        return DATE_FORMAT.format(borrowDate);
    }

    public String getFormattedReturnDate() {
        return DATE_FORMAT.format(returnDate);
    }

    public ImageView getImageView() {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(image)));
        imageView.setFitWidth(90); // Đặt chiều rộng
        imageView.setFitHeight(120); // Đặt chiều cao
        return imageView;
    }
}