package com.example.mtchat.Model;

import org.json.JSONObject;

import java.util.List;

public class Message
{
    private int id;
    private String type;
    private String sender;
    Data data;
    private String created_at;
    private String updated_at;

    public Message(String type, String sender) {
        this.type = type;
        this.sender = sender;
        this.data = data;
    }

    // Getter Methods

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public Data getData() {
        return data;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    // Setter Methods

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}

class Data {
    private String text;

    public Data(String text) {
        this.text = text;
    }

// Getter Methods

    public String getText() {
        return text;
    }

    // Setter Methods

    public void setText(String text) {
        this.text = text;
    }
}