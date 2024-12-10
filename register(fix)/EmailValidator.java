package com.example.tuanq;

import java.util.regex.Pattern;

public class EmailValidator {
    public static boolean isValidGmail(String email) {
        // Định nghĩa pattern cho email kết thúc bằng @gmail.com
        String emailRegex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";

        // Kiểm tra email với pattern
        return Pattern.matches(emailRegex, email);
    }
}
