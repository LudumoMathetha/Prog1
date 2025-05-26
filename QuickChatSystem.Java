import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class QuickChat {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to QuickChat!");

        // Login
        System.out.print("Enter username: ");
        String username = input.nextLine();

        System.out.print("Enter password: ");
        String password = input.nextLine();

        User user = new User(username, password);

        if (!user.validateUsername()) {
            System.out.println("Username must be at least 5 characters.");
            return;
        }

        if (!user.validatePassword()) {
            System.out.println("Password must include uppercase, lowercase, digit, and special char.");
            return;
        }

        System.out.print("How many messages do you want to send? ");
        int totalMessages = Integer.parseInt(input.nextLine());

        List<Message> sentMessages = new ArrayList<>();
        int sentCount = 0;

        while (true) {
            System.out.println("\n1. Send Message\n2. Show Recent Messages\n3. Quit");
            System.out.print("Choose option: ");
            int option = Integer.parseInt(input.nextLine());

            switch (option) {
                case 1:
                    if (sentCount >= totalMessages) {
                        System.out.println("Message limit reached.");
                        break;
                    }

                    System.out.print("Enter recipient cell number (e.g. +27831234567): ");
                    String recipient = input.nextLine();

                    System.out.print("Enter your message (max 250 chars): ");
                    String content = input.nextLine();

                    if (content.length() > 250) {
                        System.out.println("Message exceeds 250 characters by " + (content.length() - 250));
                        break;
                    }

                    Message msg = new Message(sentCount + 1, recipient, content);

                    if (!msg.checkRecipientCell()) {
                        System.out.println("Cell number is incorrectly formatted.");
                        break;
                    }

                    System.out.println("Choose action: send / store / disregard");
                    String action = input.nextLine();
                    System.out.println(msg.sentMessage(action));

                    if (action.equalsIgnoreCase("send") || action.equalsIgnoreCase("store")) {
                        sentMessages.add(msg);
                        sentCount++;
                        if (action.equalsIgnoreCase("store")) {
                            MessageStorage.storeMessage(msg);
                        }
                        JOptionPane.showMessageDialog(null, msg.printMessage());
                    }
                    break;

                case 2:
                    System.out.println("Coming Soon.");
                    break;

                case 3:
                    System.out.println("Exiting. Total messages sent: " + sentCount);
                    input.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

