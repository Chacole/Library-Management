package com.example.tuanq.customer;

public class Review {

    private int ID;
    public int DocumentID;
    public int UserID;
    public String UserName;
    public double Rating;
    public String Comment;

    public Review(int DocumentID, int UserID, String UserName, double Rating, String Comment) {
        this.DocumentID = DocumentID;
        this.UserID = UserID;
        this.UserName = UserName;
        this.Rating = Rating;
        this.Comment = Comment;
    }

    public int getUserID() {
        return UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public double getRating() {
        return Rating;
    }

    public String getComment() {
        return Comment;
    }
}