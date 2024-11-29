package com.example.tuanq.ExampleValues;

import com.example.tuanq.BorrowRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Calendar;
import java.util.Date;

public class BorrowFactory {
    public static ObservableList<BorrowRecord> createBorrowRecords() {
        ObservableList<BorrowRecord> borrowRecords = FXCollections.observableArrayList();

        // Tạo ngày mượn và ngày trả
        Date borrowDate1 = new Date(); // Ngày mượn là hôm nay
        Date returnDate1 = addDays(borrowDate1, 14); // Ngày trả sau 14 ngày

        Date borrowDate2 = new Date();
        Date returnDate2 = addDays(borrowDate2, 7); // Ngày trả sau 7 ngày

        Date borrowDate3 = new Date();
        Date returnDate3 = addDays(borrowDate3, 30); // Ngày trả sau 30 ngày

        // Tạo các bản ghi BorrowRecord
        BorrowRecord record1 = new BorrowRecord("User1", "World Class", borrowDate1, returnDate1, "/com/example/tuanq/images/cr7.jpeg");
        BorrowRecord record2 = new BorrowRecord("User2", "GOAT", borrowDate2, returnDate2, "/com/example/tuanq/images/Cristiano.jpg");
        BorrowRecord record3 = new BorrowRecord("User3", "Sleepy", borrowDate3, returnDate3, "/com/example/tuanq/images/Night.jpg");

        // Thêm các bản ghi vào danh sách
        borrowRecords.add(record1);
        borrowRecords.add(record2);
        borrowRecords.add(record3);

        return borrowRecords;
    }

    // Phương thức thêm số ngày vào một ngày
    private static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }
}