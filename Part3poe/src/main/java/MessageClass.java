
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class MessageClass {

    private static final List<MessageClass> sentMessages = new ArrayList<>();
    private static final List<MessageClass> disregardedMessages = new ArrayList<>();
    private static final List<MessageClass> storedMessages = new ArrayList<>();
    private static final Set<String> messageHashes = new HashSet<>();
    private static final Set<String> messageIDs = new HashSet<>();
    private static final String STORED_FILE = "stored_messages.json";

    public static void main(String[] args) {
        if (!authenticateUser()) return;

        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
        loadStoredMessages();

        boolean running = true;
        while (running) {
            String option = JOptionPane.showInputDialog("""
                    Choose an option:
                    1) Send Messages
                    2) Show recently sent messages
                    3) Search Message ID
                    4) Search by Recipient
                    5) Delete by Hash
                    6) Longest Sent Message
                    7) Full Sent Report
                    8) Quit""");

            if (option == null) break;

            switch (option) {
                case "1" -> sendMessages();
                case "2" -> showRecentMessages();
                case "3" -> searchByMessageID();
                case "4" -> searchByRecipient();
                case "5" -> deleteByHash();
                case "6" -> displayLongestMessage();
                case "7" -> displaySentReport();
                case "8" -> running = false;
                default -> JOptionPane.showMessageDialog(null, "Invalid option. Please choose 1-8.");
            }
        }
    }

    private static boolean authenticateUser() {
        String username = JOptionPane.showInputDialog("Enter username:");
        String password = JOptionPane.showInputDialog("Enter password:");
        return username != null && password != null && !username.isBlank() && !password.isBlank();
    }

    private static void sendMessages() {
        String input = JOptionPane.showInputDialog("How many messages do you want to send?");
        if (input == null) return;

        int total;
        try {
            total = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number.");
            return;
        }

        for (int i = 0; i < total; i++) {
            String recipient = JOptionPane.showInputDialog("Enter recipient cell (+codeNumber):");
            if (recipient == null) return;

            String content = JOptionPane.showInputDialog("Enter message (max 250 characters):");
            if (content == null) return;

            if (content.length() > 250) {
                JOptionPane.showMessageDialog(null, "Message too long. Max 250 characters.");
                i--;
                continue;
            }

            MessageClass msg = new MessageClass(recipient, content);

            if (!msg.checkRecipientCell()) {
                JOptionPane.showMessageDialog(null, "Invalid recipient number format.");
                i--;
                continue;
            }

            if (messageIDs.contains(msg.getId())) {
                JOptionPane.showMessageDialog(null, "Duplicate Message ID detected. Try again.");
                i--;
                continue;
            }

            String[] options = {"Send Message", "Disregard", "Store for later"};
            int choice = JOptionPane.showOptionDialog(null, "Choose action", "Message Option",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0 -> {
                    sentMessages.add(msg);
                    messageHashes.add(msg.getHash());
                    messageIDs.add(msg.getId());
                    MessageClass.incrementMessageCount();
                    JOptionPane.showMessageDialog(null, "Message sent:\n" + msg.printMessage());
                }
                case 1 -> {
                    disregardedMessages.add(msg);
                    JOptionPane.showMessageDialog(null, "Message disregarded.");
                }
                case 2 -> {
                    storeMessage(msg);
                    JOptionPane.showMessageDialog(null, "Message stored for later.");
                }
                default -> JOptionPane.showMessageDialog(null, "No valid choice made.");
            }
        }

        JOptionPane.showMessageDialog(null, "Total messages sent: " + MessageClass.getTotalMessages());
    }

    private static void showRecentMessages() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages sent yet.");
            return;
        }

        StringBuilder sb = new StringBuilder("Recently Sent Messages:\n\n");
        for (int i = Math.max(0, sentMessages.size() - 5); i < sentMessages.size(); i++) {
            sb.append(sentMessages.get(i).printMessage()).append("\n--------------------\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void storeMessage(MessageClass msg) {
        storedMessages.add(msg);
        JSONArray jsonArray = new JSONArray();

        for (MessageClass m : storedMessages) {
            JSONObject obj = new JSONObject();
            obj.put("id", m.getId());
            obj.put("hash", m.getHash());
            obj.put("recipient", m.getRecipient());
            obj.put("content", m.getContent());
            jsonArray.put(obj);
        }

        try (FileWriter file = new FileWriter(STORED_FILE)) {
            file.write(jsonArray.toString(4)); // Pretty print JSON
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving stored message.");
        }
    }

    private static void loadStoredMessages() {
        File file = new File(STORED_FILE);
        if (!file.exists()) return;

        try (FileReader reader = new FileReader(file)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONArray jsonArray = new JSONArray(tokener);

            storedMessages.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String id = obj.getString("id");
                String hash = obj.getString("hash");
                String recipient = obj.getString("recipient");
                String content = obj.getString("content");

                MessageClass msg = new MessageClass(recipient, content);
                msg.setId(id);   // You must define setId in MessageClass
                msg.setHash(hash); // You must define setHash in MessageClass
                storedMessages.add(msg);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to load stored messages.");
        }
    }

    private static void searchByMessageID() {
        String id = JOptionPane.showInputDialog("Enter Message ID to search:");
        if (id == null) return;

        for (MessageClass m : sentMessages) {
            if (m.getId().equals(id)) {
                JOptionPane.showMessageDialog(null, "Found:\nRecipient: " + m.getRecipient() + "\nMessage: " + m.getContent());
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message ID not found.");
    }

    private static void searchByRecipient() {
        String recipient = JOptionPane.showInputDialog("Enter recipient to search:");
        if (recipient == null) return;

        StringBuilder sb = new StringBuilder();
        for (MessageClass m : sentMessages) {
            if (m.getRecipient().equals(recipient)) {
                sb.append(m.printMessage()).append("\n\n");
            }
        }

        if (sb.isEmpty()) sb.append("No messages found for that recipient.");
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void deleteByHash() {
        String hash = JOptionPane.showInputDialog("Enter message hash to delete:");
        if (hash == null) return;

        Iterator<MessageClass> iter = sentMessages.iterator();
        while (iter.hasNext()) {
            MessageClass m = iter.next();
            if (m.getHash().equals(hash)) {
                iter.remove();
                messageHashes.remove(hash);
                messageIDs.remove(m.getId());
                JOptionPane.showMessageDialog(null, "Message deleted.");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message hash not found.");
    }

    private static void displayLongestMessage() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages sent.");
            return;
        }

        MessageClass longest = Collections.max(sentMessages, Comparator.comparingInt(m -> m.getContent().length()));
        JOptionPane.showMessageDialog(null, "Longest Message:\n" + longest.printMessage());
    }

    private static void displaySentReport() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages sent.");
            return;
        }

        StringBuilder sb = new StringBuilder("Sent Messages Report:\n\n");
        for (MessageClass m : sentMessages) {
            sb.append(m.printMessage()).append("\n--------------------\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}