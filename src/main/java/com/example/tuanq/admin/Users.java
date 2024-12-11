package com.example.tuanq.admin;

import java.util.Objects;

public class Users {
    private int ID;
    protected String Name;
    protected String Email;
    protected String Address;
    protected String Phone;

    public Users() {}

    /**
     * constructor.
     * @param name
     * @param email
     * @param address
     * @param phone
     */
    public Users(String name, String email, String address, String phone) {
        this.Name = name;
        this.Email = email;
        this.Address = address;
        this.Phone = phone;
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
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }

    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        this.Email = email;
    }

    public String getAddress() {
        return Address;
    }
    public void setAddress(String address) {
        this.Address = address;
    }

    public String getPhone() {
        return Phone;
    }
    public void setPhone(String phone) {
        this.Phone = phone;
    }

    /**
     * Print information of users.
     */
    public void getInfo() {
        System.out.println("ID: " + ID + "name: " + Name + "email: " + Email
                + "address: " + Address + "phone: " + Phone);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Users) {
            Users u = (Users) o;
            if (u.Name.equals(this.Name) && u.Email.equals(this.Email)
                    && u.Address.equals(this.Address) && u.Phone.equals(this.Phone)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name, Email, Address, Phone);
    }
}
