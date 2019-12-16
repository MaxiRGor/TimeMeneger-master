package com.example.myapplication.secret;

public class Secret {
    private String id;
    private String text;
    private String userUID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public Secret() {
    }

    public Secret(String id, String text, String userUID) {
        this.id = id;
        this.text = text;
        this.userUID = userUID;
    }


}
