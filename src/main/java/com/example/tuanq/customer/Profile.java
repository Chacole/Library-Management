package com.example.tuanq.customer;

public class Profile {
    private static Profile instance;
    private String username;
    private String email;
    private String role;

    // Private constructor để ngăn chặn việc tạo nhiều đối tượng
    private Profile() {}

    // Singleton instance
    public static Profile getInstance() {
        if (instance == null) {
            instance = new Profile();
        }
        return instance;
    }

    // Getters và setters cho thông tin người dùng
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Phương thức để xóa session khi người dùng đăng xuất
    public void clearSession() {
        instance = null;
    }
}
