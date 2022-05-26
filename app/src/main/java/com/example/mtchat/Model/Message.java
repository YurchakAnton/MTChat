package com.example.mtchat.Model;


import java.util.Date;

public class Message
{
    private String messageText, messageUser, userEmail, typeOfMessage, image;
    private long messageTime;

    public Message(String messageText, String messageUser, String userEmail, long messageTime, String typeOfMessage) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.userEmail = userEmail;
        this.messageTime = messageTime;
        this.typeOfMessage = typeOfMessage;
        this.image = image;
    }

    public Message()
    {
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getTypeOfMessage() {
        return typeOfMessage;
    }

    public void setTypeOfMessage(String typeOfMessage) {
        this.typeOfMessage = typeOfMessage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}