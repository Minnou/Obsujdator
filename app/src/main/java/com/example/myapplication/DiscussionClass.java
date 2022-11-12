package com.example.myapplication;

public class DiscussionClass {
    private String title;
    private String text;
    public DiscussionClass(String title, String text){
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
}
