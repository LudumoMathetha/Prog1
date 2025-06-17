import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class QuickChat {
    static List<String> sentMessages = new ArrayList<>();
    static List<String> disregardedMessages = new ArrayList<>();
    static List<String> storedMessages = new ArrayList<>();
    static List<String> messageHashes = new ArrayList<>();
    static List<String> messageIDs = new ArrayList<>();
    static Map<String, String> messageRecipients = new HashMap<>();
    static Map<String, String> messageMap = new HashMap<>();

    public static void main(String[] args) {
        // Sample test data setup
        addMessage("+27834557896", "Did you get the cake?", "Sent");
        addMessage("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        addMessage("+27834484567", "Yohoooo, I am at your gate.", "Disregard");
        addMessage("0838884567", "It is dinner time!", "Sent");
        addMessage("+27838884567", "Ok, I am leaving without you.", "Stored");

        // Display Report
        displayReport();

        // Display longest message
        displayLongestMessage();

        // Search by ID
        String searchID = messageIDs.get(3);
        System.out.println("\nSearch by ID (" + searchID + "):");
        System.out.println("Recipient: " + messageRecipients.get(searchID));
        System.out.println("Message: " + messageMap.get(searchID));

        // Search by recipient
        String recipientToSearch = "+27838884567";
        System.out.println("\nMessages sent to " + recipientToSearch + ":");
        for (String id : messageIDs) {
            if (messageRecipients.get(id).equals(recipientToSearch)) {
                System.out.println(messageMap.get(id));
            }
        }

        // Delete a message by hash
        String hashToDelete = messageHashes.get(1); // Example: message 2
        deleteMessageByHash(hashToDelete);
    }

    static void addMessage(String recipient, String message, String flag) {
        String id = UUID.randomUUID().toString();
        String hash = generateHash(message);

        switch (flag) {
            case "Sent":
                sentMessages.add(message);
                break;
            case "Stored":
                storedMessages.add(message);
                break;
            case "Disregard":
                disregardedMessages.add(message);
                return;
        }

        messageIDs.add(id);
        messageHashes.add(hash);
        messageMap.put(id, message);
        messageRecipients.put(id, recipient);
    }

    static void displayReport() {
        System.out.println("\n--- Sent Message Report ---");
        for (int i = 0; i < sentMessages.size(); i++) {
            String msg = sentMessages.get(i);
            String id = messageIDs.get(i);
            String hash = messageHashes.get(i);
            String recipient = messageRecipients.get(id);
            System.out.println("Message ID: " + id);
            System.out.println("Message Hash: " + hash);
            System.out.println("Recipient: " + recipient);
            System.out.println("Message: " + msg);
            System.out.println("--------------------------");
        }
    }

    static void displayLongestMessage() {
        String longest = "";
        for (String msg : sentMessages) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }
        System.out.println("\nLongest Sent Message:");
        System.out.println(longest);
    }

    static void deleteMessageByHash(String hash) {
        int index = messageHashes.indexOf(hash);
        if (index != -1) {
            String id = messageIDs.get(index);
            String message = messageMap.get(id);
            sentMessages.remove(message);
            System.out.println("\nMessage \"" + message + "\" successfully deleted.");
        } else {
            System.out.println("\nMessage hash not found.");
        }
    }

    static String generateHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}


