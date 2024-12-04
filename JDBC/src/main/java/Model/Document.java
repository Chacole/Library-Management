package Model;

public class Document {
    public int ID;
    public String Author;
    public String Title;
    public String Type;
    public int Year;
    public int Quantity;
    public String ImagePath;

    public Document(int ID, String Author, String Title,
                    String Type, int Year,
                    int Quantity, String ImagePath) {
        this.ID = ID;
        this.Title = Title;
        this.Type = Type;
        this.Author = Author;
        this.Year = Year;
        this.Quantity = Quantity;
        this.ImagePath = ImagePath;
    }
}
