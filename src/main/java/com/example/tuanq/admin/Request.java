package com.example.tuanq.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Request {
    private int id;
    private String userName;
    private int documentID;
    private String status;
    private String type;
    private String documentTitle;
    private Date borrowDate;
    private Date returnDate;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public Request(String userName, int bookId, String type) {
        this.userName = userName;
        this.documentID = bookId;
        this.type = type;
    }

    public Request(String userName, int bookId, String type, String documentTitle) {
        this.userName = userName;
        this.documentID = bookId;
        this.type = type;
        this.documentTitle = documentTitle;
    }

    public Request(int id, String userName, int bookId, String documentTitle, String status, String type) {
        this.id = id;
        this.userName = userName;
        this.documentID = bookId;
        this.status = status;
        this.type = type;
        this.documentTitle = documentTitle;
    }

    // Getters and Setters
    public int getId() { return id; }

    public String getUserName() { return userName; }

    public int getDocumentId() { return documentID; }

    public String getStatus() { return status; }

    public String getType() { return type; }

    public String getTitle() { return documentTitle; }

    public void setStatus(String status) { this.status = status; }

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
}