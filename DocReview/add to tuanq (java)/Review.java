package com.example.tuanq;

public class Review {

    private int ID;
    public int DocumentID;
    public int UserID;
    public String UserName;
    public Double Rating;
    public String Comment;

    public Review(int ID, int DocumentID, int UserID, String UserName, Double Rating, String Comment) {
        this.ID = ID;
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

    public Double getRating() {
        return Rating;
    }

    public String getComment() {
        return Comment;
    }
}
