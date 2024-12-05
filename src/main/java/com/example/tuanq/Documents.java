package com.example.tuanq;

import java.util.*;

public class Documents {
    protected int ID;
    protected String Author;
    protected String Title;
    protected String Type;
    protected int YearPublished;
    protected int Quantity;
    protected String imagePath;

    public Documents() {}

    /**
     * constructor.
     * @param author
     * @param title
     * @param type
     * @param year
     */
    public Documents(String author, String title, String type, int year, int quantity) {
        this.Author = author;
        this.Title = title;
        this.Type = type;
        this.YearPublished = year;
        this.Quantity = quantity;
    }

    public Documents(String author, String title, String type, int year, int quantity, String imagePath) {
        this.Author = author;
        this.Title = title;
        this.Type = type;
        this.YearPublished = year;
        this.Quantity = quantity;
        this.imagePath = imagePath;
    }

    /**
     * getter and setter.
     */
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAuthor() {
        return Author;
    }
    public void setAuthor(String author) {
        this.Author = author;
    }

    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        this.Title = title;
    }

    public String getType() {
        return Type;
    }
    public void setType(String type) {
        this.Type = type;
    }

    public int getYear() {
        return YearPublished;
    }
    public void setYear(int year) {
        this.YearPublished = year;
    }

    public int getQuantity() {
        return Quantity;
    }
    public void setQuantity(int quantity) {
        this.Quantity = quantity;
    }

    public String getUrl() { return imagePath; }

    public void updateImageFromApi() {
        if (this.imagePath == null || this.imagePath.isEmpty() || !this.imagePath.startsWith("http")) {
            String apiImageUrl = ApiService.getGoogleBookImage(this.Title);
            if (apiImageUrl != null) {
                this.imagePath = apiImageUrl;
            }
        }
    }

    /**
     * print information of documents.
     */
    public void getInfo() {
        System.out.println("ID: " + this.ID + "author: " + Author + "title: " + Title
                + "type: " + Type + "year: " + YearPublished + "quantity: " + Quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Documents) {
            Documents d = (Documents) o;
            if (Title.equals(d.getTitle()) && Author.equals(d.getAuthor()) && Type.equals(d.getType()) && YearPublished == d.getYear()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Title, Author, Type, YearPublished);
    }
}

