package com.example.finalproject.models;


import java.io.Serializable;

/**
 * Created by Ulan on 28.09.2018.
 */
public class Note implements Serializable {
    private String title, text = "";
    private int id;

    public Note() {}

    public Note(String title, String text) {
        this.title = title;
        this.text = text;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
