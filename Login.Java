import java.util.regex.*;
import java.util.Scanner;

class Login {
    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    // Constructor
    public Login(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Method to check if the username is correctly formatted
    public boolean checkUserName(String username) {
        // Must contain an underscore and be max 5 characters
        if (username.matches("^(?=.*_)[a-zA-Z0-9_]{1,5}$")) {
            this.username = username;
            return true;
        }
        return false;
    }

    // Method to check if the password meets complexity requirements
    public boolean checkPasswordComplexity(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        if (password.matches(passwordRegex)) {
            this.password = password;
            return true;
        }
        return false;
    }

    // Method to check if the cellphone number is correctly formatted
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        String phoneRegex = "^\\+27[0-9]{9}$"; // South African number format with country code
        if (cellPhoneNumber.matches(phoneRegex)) {
            this.cellPhoneNumber = cellPhoneNumber;
            return true;
        }
        return false;
    }

    // Registration method
    public String registerUser(String username, String password, String cellPhoneNumber) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cell phone number incorrectly formatted or does not contain an international code.";
        }
        return "User registered successfully.";
    }

    // Login method
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return enteredUsername.equals(this.username) && enteredPassword.equals(this.password);
    }

    // Method to return login status
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + firstName + " " + lastName + " (" + username + ")! It is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}

// Main class to execute the application
public class RegistrationLoginSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Collect user details
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine().trim();

        // Basic validation for names
        if (!firstName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
            System.out.println("Names must only contain alphabetic characters.");
            scanner.close();
            return;
        }

        Login login = new Login(firstName, lastName);

        // Registration
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter cell phone number (e.g., +27812345678): ");
        String cellPhoneNumber = scanner.nextLine();

        String registrationMessage = login.registerUser(username, password, cellPhoneNumber);
        System.out.println(registrationMessage);

        if (!registrationMessage.equals("User registered successfully.")) {
            System.out.println("Registration failed. Please try again.");
            scanner.close();
            return;
        }

        // Login
        System.out.print("Enter username to login: ");
        String enteredUsername = scanner.nextLine();
        System.out.print("Enter password to login: ");
        String enteredPassword = scanner.nextLine();

        boolean loginSuccess = login.loginUser(enteredUsername, enteredPassword);
        System.out.println(login.returnLoginStatus(loginSuccess));

        // Close the scanner to prevent resource leaks
        scanner.close();
    }
}
