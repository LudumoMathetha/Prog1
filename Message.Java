import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.swing.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Message {
    static int sentCounter = 0;
    String messageID;
    String recipient;
    String messageText;
    String messageHash;

    public void createMessage() throws IOException {
        Scanner scanner = new Scanner(System.in);
        this.messageID = generateMessageID();
        sentCounter++;

        System.out.print("Enter recipient number (include intl. code): ");
        this.recipient = scanner.nextLine();
        if (!checkRecipientCell()) {
            System.out.println("Cell phone number is incorrectly formatted or does not contain an international code.");
            return;
        }

        System.out.print("Enter message (max 250 chars): ");
        this.messageText = scanner.nextLine();
        if (this.messageText.length() > 250) {
            int over = this.messageText.length() - 250;
            System.out.println("Message exceeds 250 characters by " + over + ", please reduce size.");
            return;
        }

        System.out.println("Message ready to send.");
        this.messageHash = createMessageHash();
        String option = sentMessage();

        if (option.equals("1")) {
            QuickChatSystem.sentMessages.add(this);
            JOptionPane.showMessageDialog(null, "Message ID: " + messageID +
                    "\nHash: " + messageHash +
                    "\nRecipient: " + recipient +
                    "\nMessage: " + messageText);
        } else if (option.equals("2")) {
            System.out.println("Press 0 to delete message.");
        } else if (option.equals("3")) {
            storeMessage();
            System.out.println("Message successfully stored.");
        }
    }

    public String generateMessageID() {
        Random rand = new Random();
        return String.format("%010d", rand.nextInt(1000000000));
    }

    public boolean checkRecipientCell() {
        return recipient.length() <= 13 && recipient.startsWith("+27") && recipient.matches("^\\+27\\d{9}$");
    }

    public String createMessageHash() {
        String[] words = messageText.split(" ");
        if (words.length < 2) return messageID + ":" + sentCounter + ":HASH";
        return messageID.substring(0, 2) + ":" + sentCounter + ":" + words[0] + "_" + words[words.length - 1];
    }

    public String sentMessage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n1) View message\n2) Delete message\n3) Store message");
        System.out.print("Choose an option: ");
        return scanner.nextLine();
    }

    public void storeMessage() throws IOException {
        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("messageID", messageID);
        jsonMessage.put("recipient", recipient);
        jsonMessage.put("message", messageText);
        jsonMessage.put("hash", messageHash);

        JSONArray messagesArray;
        Path path = Paths.get("messages.json");
        if (Files.exists(path)) {
            String content = Files.readString(path);
            messagesArray = content.isEmpty() ? new JSONArray() : (JSONArray) org.json.simple.JSONValue.parse(content);
        } else {
            messagesArray = new JSONArray();
        }

        messagesArray.add(jsonMessage);
        Files.writeString(path, messagesArray.toJSONString());
    }
}
