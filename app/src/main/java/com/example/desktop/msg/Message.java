package com.example.desktop.msg;

public class Message {
    private String Sender;
    private String Receipt;
    private String Title;
    private String Content;

    public Message(String sender, String receipt, String title, String content) {
        Sender = sender;
        Receipt = receipt;
        Title = title;
        Content = content;
    }

    public String getSender() {
        return Sender;
    }

    public String getReceipt() {
        return Receipt;
    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }
}
