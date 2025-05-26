

package com.mycompany.quickchat;
 import java.util.Scanner;


      

public class QuickChatApp {
    static Scanner scanner = new Scanner(System.in);
    static boolean loggedIn = false;

    public static void main(String[] args) {
        login();

        if (loggedIn) {
            System.out.println("Welcome to QuickChat.");

            int choice;
            do {
                System.out.println("\nChoose an option:");
                System.out.println("1. Send Messages");
                System.out.println("2. Show Recently Sent Messages");
                System.out.println("3. Quit");
                System.out.print("Enter choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        sendMessages();
                        break;
                    case 2:
                        System.out.println("Coming Soon.");
                        break;
                    case 3:
                        System.out.println("Exiting application...");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } while (choice != 3);
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String user = scanner.nextLine();

        System.out.print("Enter password: ");
        String pass = scanner.nextLine();

        
        if (user.equals("admin") && pass.equals("1234")) {
            loggedIn = true;
        } else {
            System.out.println("Login failed. Exiting...");
        }
    }

    private static void sendMessages() {
        System.out.print("How many messages would you like to send? ");
        int count = scanner.nextInt();
        scanner.nextLine(); 
        
        for (int i = 1; i <= count; i++) {
            System.out.println("\nMessage " + i + ":");

            System.out.print("Enter recipient: ");
            String recipient = scanner.nextLine();

            System.out.print("Enter subject: ");
            String subject = scanner.nextLine();

            System.out.print("Enter content: ");
            String content = scanner.nextLine();

            System.out.println("Message sent to " + recipient + "!");
        }
    }
}
    

