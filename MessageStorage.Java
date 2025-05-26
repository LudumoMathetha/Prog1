import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MessageStorage {
    private static final String FILE_NAME = "messages.json";

    public static void storeMessage(Message msg) {
        JSONObject obj = new JSONObject();
        obj.put("MessageID", msg.getMessageID());
        obj.put("MessageHash", msg.getMessageHash());
        obj.put("Recipient", msg.getRecipient());
        obj.put("Message", msg.getContent());

        JSONArray msgList = new JSONArray();
        msgList.add(obj);

        try (FileWriter file = new FileWriter(FILE_NAME, true)) {
            file.write(msgList.toJSONString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
