package com.example.tuanq;

public class CheckPhone {
    // Phương thức kiểm tra số điện thoại chỉ chứa các chữ số
    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Regex kiểm tra chuỗi chỉ chứa các chữ số từ 0-9
        String phoneRegex = "^[0-9]+$";
        return phoneNumber.matches(phoneRegex);
    }
}

