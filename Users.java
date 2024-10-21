import java.util.*;

public class Users {
    private int ID;
    private String name;
    private String email;
    private String address;
    private String phone;

    public Users() {}

    /**
     * constructor.
     * @param name
     * @param email
     * @param address
     * @param phone
     */
    public Users(String name, String email, String address, String phone) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Print information of users.
     */
    public void getInfo() {
        System.out.println("ID: " + ID + "name: " + name + "email: " + email
                + "address: " + address + "phone: " + phone);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Users) {
            Users u = (Users) o;
            if (u.name.equals(this.name) && u.email.equals(this.email)
                    && u.address.equals(this.address) && u.phone.equals(this.phone)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, address, phone);
    }
}
