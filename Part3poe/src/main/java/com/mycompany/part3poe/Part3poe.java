/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



package com.mycompany.part3prog.poe;

import java.util.UUID;

public class MessageClass {
    private String id;
    private String hash;
    private String recipient;
    private String content;

    // Static counter for total messages sent
    private static int totalMessages = 0;

    public MessageClass(String recipient, String content) {
        this.id = generateId();
        this.hash = generateHash(recipient, content);
        this.recipient = recipient;
        this.content = content;
    }

    
    private String generateId() {
        return UUID.randomUUID().toString();
    }

    
    private String generateHash(String recipient, String content) {
        return Integer.toHexString((recipient + content).hashCode());
    }

    public boolean checkRecipientCell() {
      
        return recipient != null && recipient.matches("^\\+\\d+$");
    }

    public String printMessage() {
        return "ID: " + id + "\nHash: " + hash + "\nRecipient: " + recipient + "\nMessage: " + content;
    }

    public static void incrementMessageCount() {
        totalMessages++;
    }

    public static int getTotalMessages() {
        return totalMessages;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}