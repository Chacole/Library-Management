package Model;

public class User {
    public int ID;
    public String Name;
    public String Email;
    public String Phone;
    public String Address;

    // for account
    public String Password;

    public User(int ID, String Name, String Email,
                String Address, String Phone,
                String Password) {
        this.ID = ID;
        this.Name = Name;
        this.Email = Email;
        this.Phone = Phone;
        this.Address = Address;
        this.Password = Password;
    }
}
