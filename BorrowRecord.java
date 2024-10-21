import javax.swing.text.Document;
import java.util.*;

public class BorrowRecord {
    private Users users;
    private Documents documents;
    private Date borrowDate;
    private Date returnDate;

    /**
     * constructor.
     * @param users
     * @param documents
     */
    public BorrowRecord(Users users, Documents documents) {
        this.users = users;
        this.documents = documents;
        this.borrowDate = new Date(); // set the day borrow is today.
        this.returnDate = null; // the day return is unknown.
    }

    /**
     * getter and setter.
     */
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

    public Users getUsers() {
        return users;
    }
    public void setUsers(Users users) {
        this.users = users;
    }

    public Documents getDocuments() {
        return documents;
    }
    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

}
