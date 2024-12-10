package com.example.tuanq;

public class Activity {
    public String UserName;
    public String Request;
    public String Document;
    public String Status;
    public String Time;

    public Activity(String UserName, String Request, String Document,
                    String Status, String Time) {
        this.UserName = UserName;
        this.Request = Request;
        this.Document = Document;
        this.Status = Status;
        this.Time = Time;
    }

    public String getUserName() {
        return UserName;
    }

    public String getRequest() {
        return Request;
    }

    public String getDocument() {
        return Document;
    }

    public String getStatus() {
        return Status;
    }

    public String getTime() {
        return Time;
    }
}
