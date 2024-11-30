package Model;

public class Document {
    public int ID;
    public String Title;
    public String ISBN;
    public String Type;
    public String Author;
    public String Publisher;
    public int YearPublished;
    public int Quantity;

    public Document(int ID, String Title,
                    String ISBN, String Type,
                    String Author, String Publisher,
                    int YearPublished, int Quantity) {
        this.ID = ID;
        this.Title = Title;
        this.ISBN = ISBN;
        this.Type = Type;
        this.Author = Author;
        this.Publisher = Publisher;
        this.YearPublished = YearPublished;
        this.Quantity = Quantity;
    }
}
