/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testthem;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

/**
 *
 * @author RC_Student_lab
 */
public class Registration {
    private static final String 
            USERNAME_REGEX = "^[a-zA-Z0-9_]{1,5}$";
    
    private static final String
            PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?/~\\\\-])(?=.*[a-zA-Z\\d!@#$$%^&*()_+{}\\[\\]:;<>,.?/~\\\\-]).{8,}";
    
    private static final String PHONE_REGEX = "^(0[6-8][0-9]{8})$"; // Basic South African mobile number format

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Chat App!");

       
        System.out.println("\n--- Registration ---");
        String newUsername = getValidUsername(scanner);
        String newPassword = getValidPassword(scanner);
        String newPhoneNumber = getValidPhoneNumber(scanner);

      
        System.out.println("\nAccount created successfully!");
        System.out.println("Username: " + newUsername);
        System.out.println("Phone Number: " + newPhoneNumber);
        System.out.println("Note: Password is not displayed for security reasons.");

        
        System.out.println("\n--- Login ---");
        System.out.print("Enter your username: ");
        String loginUsername = scanner.nextLine();
        System.out.print("Enter your password: ");
        String loginPassword = scanner.nextLine();

       
        if (loginUsername.equals(newUsername) && loginPassword.equals(newPassword)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }

        scanner.close();
    }

    public static String getValidUsername(Scanner scanner) {
        String username;
        boolean isValid;
        do {
            System.out.print("Enter your username (max 5 characters, must contain an underscore): ");
            username = scanner.nextLine();
            isValid = Pattern.matches(USERNAME_REGEX, username) && username.contains("_");
            if (!isValid) {
                System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        } while (!isValid);
        return username;
    }

    public static String getValidPassword(Scanner scanner) {
        String password;
        boolean isValid;
        do {
            System.out.print("Enter your password (at least 8 characters, with a capital letter, a number, and a special character): ");
            password = scanner.nextLine();
            isValid = Pattern.matches(PASSWORD_REGEX, password);
            if (!isValid) {
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        } while (!isValid);
        return password;
    }

    public static String getValidPhoneNumber(Scanner scanner) {
        String phoneNumber;
        boolean isValid;
        do {
            System.out.print("Enter your South African phone number (e.g., 0721234567): ");
            phoneNumber = scanner.nextLine();
            isValid = Pattern.matches(PHONE_REGEX, phoneNumber);
            if (!isValid) {
                System.out.println("Invalid South African phone number format. Please enter a valid number starting with 06, 07, or 08 followed by 8 digits.");
            }
        } while (!isValid);
        return phoneNumber;
    }
}
    
   
