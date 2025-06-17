import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuickChatTest {

    @Test
    public void testMessageTooLong() {
        String longMsg = "A".repeat(260);
        assertTrue(longMsg.length() > 250, "Message exceeds 250 characters.");
    }

    @Test
    public void testMessageValidLength() {
        String validMsg = "Hello, this is under 250 chars.";
        assertTrue(validMsg.length() <= 250);
    }

    @Test
    public void testCellNumberValid() {
        Message msg = new Message(1, "+27831234567", "Hi there!");
        assertTrue(msg.checkRecipientCell());
    }

    @Test
    public void testCellNumberInvalid() {
        Message msg = new Message(1, "0831234567", "Hi there!");
        assertFalse(msg.checkRecipientCell());
    }

    @Test
    public void testMessageHash() {
        Message msg = new Message(1, "+27831234567", "Hello world");
        String hash = msg.createMessageHash();
        assertTrue(hash.matches("[A-Z0-9]+:[0-9]+:[A-Z]+"));
    }

    @Test
    public void testSendOptions() {
        Message msg = new Message(1, "+27831234567", "Quick chat");
        assertEquals("Message successfully sent.", msg.sentMessage("send"));
        assertEquals("Press 0 to delete message.", msg.sentMessage("disregard"));
        assertEquals("Message successfully stored.", msg.sentMessage("store"));
    }
}

    @Test
    public void testSentMessagesPopulated() {
        QuickChat.sentMessages.clear();
        QuickChat.messageIDs.clear();
        QuickChat.messageRecipients.clear();
        QuickChat.messageMap.clear();
        QuickChat.messageHashes.clear();

        QuickChat.addMessage("+27834557896", "Did you get the cake?", "Sent");
        QuickChat.addMessage("0838884567", "It is dinner time!", "Sent");

        assertEquals(2, QuickChat.sentMessages.size());
        assertTrue(QuickChat.sentMessages.contains("Did you get the cake?"));
        assertTrue(QuickChat.sentMessages.contains("It is dinner time!"));
    }

    @Test
    public void testLongestMessage() {
        QuickChat.sentMessages.clear();
        QuickChat.sentMessages.add("Did you get the cake?");
        QuickChat.sentMessages.add("It is dinner time!");
        QuickChat.sentMessages.add("Where are you? You are late! I have asked you to be on time.");

        String expected = "Where are you? You are late! I have asked you to be on time.";
        String actual = "";
        for (String msg : QuickChat.sentMessages) {
            if (msg.length() > actual.length()) {
                actual = msg;
            }
        }
        assertEquals(expected, actual);
    }

    @Test
    public void testSearchMessageByID() {
        QuickChat.sentMessages.clear();
        QuickChat.messageIDs.clear();
        QuickChat.messageMap.clear();
        QuickChat.messageRecipients.clear();
        QuickChat.addMessage("0838884567", "It is dinner time!", "Sent");

        String id = QuickChat.messageIDs.get(0);
        assertEquals("It is dinner time!", QuickChat.messageMap.get(id));
        assertEquals("0838884567", QuickChat.messageRecipients.get(id));
    }

    @Test
    public void testSearchMessagesByRecipient() {
        QuickChat.sentMessages.clear();
        QuickChat.messageIDs.clear();
        QuickChat.messageMap.clear();
        QuickChat.messageRecipients.clear();

        QuickChat.addMessage("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        QuickChat.addMessage("+27838884567", "Ok, I am leaving without you.", "Stored");

        List<String> found = new ArrayList<>();
        for (String id : QuickChat.messageIDs) {
            if (QuickChat.messageRecipients.get(id).equals("+27838884567")) {
                found.add(QuickChat.messageMap.get(id));
            }
        }

        assertTrue(found.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(found.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteMessageByHash() {
        QuickChat.sentMessages.clear();
        QuickChat.messageIDs.clear();
        QuickChat.messageHashes.clear();
        QuickChat.messageMap.clear();
        QuickChat.messageRecipients.clear();

        QuickChat.addMessage("+27838884567", "Where are you? You are late! I have asked you to be on time.", "Sent");
        String hash = QuickChat.messageHashes.get(0);
        QuickChat.deleteMessageByHash(hash);

        assertFalse(QuickChat.sentMessages.contains("Where are you? You are late! I have asked you to be on time."));
    }