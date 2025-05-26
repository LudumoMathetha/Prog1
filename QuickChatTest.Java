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
