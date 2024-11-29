package com.example.tuanq.ExampleValues;

import com.example.tuanq.ManagementID;
import com.example.tuanq.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Định nghĩa abstract class
public abstract class UserFactory {

    // Phương thức static để tạo danh sách Users
    public static ObservableList<Users> createUsers() {
        ObservableList<Users> users = FXCollections.observableArrayList();
        ManagementID idManager = new ManagementID();

        for (int i = 1; i <= 99; i++) {
            Users user = new Users("User" + i, "user" + i + "@example.com", "Address " + i, "Phone " + i);
            user.assignID(idManager);
            users.add(user);
        }

        return users;
    }
}