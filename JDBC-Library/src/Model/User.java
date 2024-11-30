package Model;

public class User {
    public int ID;
    public String FullName;
    public String BirthDate;
    public String Email;
    public String Phone;
    public String Address;

    // for account
    public String Password;

    public User(int ID, String FullName,
                String BirthDate, String Email,
                String Phone, String Address,
                String Password) {
        this.ID = ID;
        this.FullName = FullName;
        this.BirthDate = BirthDate;
        this.Email = Email;
        this.Phone = Phone;
        this.Address = Address;
        this.Password = Password;
    }
}
