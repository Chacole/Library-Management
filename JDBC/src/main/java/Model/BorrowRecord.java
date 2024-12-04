package Model;

public class BorrowRecord {
    public int BorrowID;
    public int UserID;
    public String UserName;
    public int DocumentID;
    public String DocumentTitle;
    public String BorrowDate;
    public String ReturnDate;

    public BorrowRecord(int BorrowID,
                        int UserID, String UserName,
                        int DocumentID, String DocumentTitle,
                        String BorrowDate, String ReturnDate) {
        this.BorrowID = BorrowID;
        this.UserID = UserID;
        this.UserName = UserName;
        this.DocumentID = DocumentID;
        this.DocumentTitle = DocumentTitle;
        this.BorrowDate = BorrowDate;
        this.ReturnDate = ReturnDate;
    }
}
