package com.example.myapplication;

public class ReplyClass {
    private String id;
    private String title;
    private String text;
    public ReplyClass(String id, String title, String text){
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
