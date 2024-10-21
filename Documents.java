import java.util.*;

public class Documents{
    protected int ID;
    protected String author;
    protected String title;
    protected String type;
    protected int year;
    protected int quantity;

    public Documents() {}

    /**
     * constructor.
     * @param author
     * @param title
     * @param type
     * @param year
     */
     public Documents(String author, String title, String type, int year, int quantity) {
        this.author = author;
        this.title = title;
        this.type = type;
        this.year = year;
        this.quantity = quantity;
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
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * print information of documents.
     */
    public void getInfo() {
        System.out.println("ID: " + ID + "author: " + author + "title: " + title
                + "type: " + type + "year: " + year + "quantity: " + quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Documents) {
            Documents d = (Documents) o;
            if (title.equals(d.getTitle()) && author.equals(d.getAuthor()) && type.equals(d.getType()) && year == d.getYear()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, type, year);
    }
}
