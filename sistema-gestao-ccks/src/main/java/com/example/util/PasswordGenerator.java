package com.example.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordGenerator {
    public static void main(String[] args) {
        String passwordToHash = "admin123";
        String hashedPassword = BCrypt.hashpw(passwordToHash, BCrypt.gensalt());
        System.out.println("Use este hash para o usuário 'admin': " + hashedPassword);
    }
}